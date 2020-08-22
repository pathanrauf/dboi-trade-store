package com.dboi.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dboi.AbstractTest;
import com.dboi.entity.TradeStoreEntity;
import com.dboi.repository.TradeStoreRepository;
import com.dboi.service.TradeService;

public class TradeStoreControllerTest extends AbstractTest
{
	@Mock
	private TradeStoreRepository tradeStoreRepository;

	@Autowired
	private TradeService tradeService;

	@Override
	@Before
	public void setUp() throws ParseException {
		super.setUp();

		tradeStoreRepository
				.save(new TradeStoreEntity("T1", 1, "CP-1", "B1", new Date(sdf.parse("20-05-2020").getTime()), new Date(new java.util.Date().getTime()), "N"));
		tradeStoreRepository
				.save(new TradeStoreEntity("T2", 2, "CP-2", "B1", new Date(sdf.parse("20-05-2021").getTime()), new Date(new java.util.Date().getTime()), "N"));
		tradeStoreRepository
				.save(new TradeStoreEntity("T2", 1, "CP-1", "B1", new Date(sdf.parse("20-05-2021").getTime()), new Date(sdf.parse("14-03-2015").getTime()), "N"));
		tradeStoreRepository
				.save(new TradeStoreEntity("T3", 3, "CP-3", "B2", new Date(sdf.parse("20-05-2014").getTime()), new Date(new java.util.Date().getTime()), "Y"));
	}

	@Test
	public void getTradeStore() throws Exception {
		String uri = "/trade-store/t1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		TradeStoreEntity tradeStoreEntity = super.mapFromJson(content, TradeStoreEntity.class);
		assertNotNull(tradeStoreEntity);
		assertEquals("T1", tradeStoreEntity.getTradeId());
	}

	@Test
	public void getTradeStore_withMultipleVersions() throws Exception {
		String uri = "/trade-store/t2";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		TradeStoreEntity tradeStoreEntity = super.mapFromJson(content, TradeStoreEntity.class);
		assertNotNull(tradeStoreEntity);
		assertEquals("T2", tradeStoreEntity.getTradeId());
	}

	@Test
	public void saveTradeStore_oldVersionTrade() throws Exception {

		String uri = "/trade-store";
		TradeStoreEntity tradeStoreEntity = new TradeStoreEntity("T2", 1, "CP-1", "B1", new Date(sdf.parse("20-05-2021").getTime()),
				new Date(sdf.parse("14-03-2015").getTime()), "N");
		String tradeStoreEntityContent = super.mapToJson(tradeStoreEntity);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON_VALUE).content(tradeStoreEntityContent).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResolvedException().getMessage();
		assertEquals(content, "Old Version Trade not Allowed");
	}

	@Test
	public void saveTradeStore_expiredTrade() throws Exception {

		String uri = "/trade-store";
		TradeStoreEntity tradeStoreEntity = new TradeStoreEntity("T3", 3, "CP-3", "B2", new Date(sdf.parse("20-05-2014").getTime()),
				new Date(new java.util.Date().getTime()), "N");

		String tradeStoreEntityContent = super.mapToJson(tradeStoreEntity);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON_VALUE).content(tradeStoreEntityContent).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResolvedException().getMessage();
		assertEquals(content, "Trade has expired");
	}

	@Test
	public void saveTradeStore_updatedSuccessfully() throws Exception {

		String uri = "/trade-store";
		TradeStoreEntity tradeStoreEntity = new TradeStoreEntity("T2", 2, "CP-2", "B2", new Date(sdf.parse("20-05-2021").getTime()),
				new Date(new java.util.Date().getTime()), "N");
		String tradeStoreEntityContent = super.mapToJson(tradeStoreEntity);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON_VALUE).content(tradeStoreEntityContent).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		tradeStoreEntity = super.mapFromJson(content, TradeStoreEntity.class);
		assertNotNull(tradeStoreEntity);
		assertEquals("B2", tradeStoreEntity.getBookId());
	}

	@Test
	public void saveTradeStore_createdSuccessfully() throws Exception {

		String uri = "/trade-store";
		TradeStoreEntity tradeStoreEntity = new TradeStoreEntity("T4", 1, "CP-2", "B2", new Date(sdf.parse("20-05-2021").getTime()),
				new Date(new java.util.Date().getTime()), "N");
		String tradeStoreEntityContent = super.mapToJson(tradeStoreEntity);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON_VALUE).content(tradeStoreEntityContent).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		tradeStoreEntity = super.mapFromJson(content, TradeStoreEntity.class);
		assertNotNull(tradeStoreEntity);
		assertEquals("B2", tradeStoreEntity.getBookId());
	}

	@Test
	public void markExpired() throws Exception {

		List<TradeStoreEntity> tradeStoreEntityList = tradeService.markExpired();
		assertNotNull(tradeStoreEntityList);
		assertFalse(tradeStoreEntityList.isEmpty());
		assertEquals("Y", tradeStoreEntityList.get(0).getExpired());
	}

}
