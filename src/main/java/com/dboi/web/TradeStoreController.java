package com.dboi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dboi.entity.TradeStoreEntity;
import com.dboi.service.TradeService;

@RestController
@RequestMapping("/trade-store")
public class TradeStoreController
{
	@Autowired
	private TradeService tradeService;

	@GetMapping("/{id}")
	public TradeStoreEntity getTradeById(@PathVariable("id") String tradeId) {
		return tradeService.getTradeById(tradeId);
	}

	@PostMapping
	public TradeStoreEntity createOrUpdateTrade(@RequestBody TradeStoreEntity trade) {
		return tradeService.storeTrade(trade);
	}
}