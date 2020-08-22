package com.dboi.service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dboi.entity.TradeStoreEntity;
import com.dboi.exception.RecordNotFoundException;
import com.dboi.repository.TradeStoreRepository;

@Service
public class TradeService
{
	
	@Autowired
	private TradeStoreRepository tradeRepo;

	public TradeStoreEntity getTradeById(String tradeId) throws RecordNotFoundException {
		Optional<TradeStoreEntity> tradeStoreEntity = getTradeStoreEntity(tradeId);

		if (tradeStoreEntity.isPresent()) {
			return tradeStoreEntity.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	public TradeStoreEntity storeTrade(TradeStoreEntity tradeStore) throws RecordNotFoundException {
		Optional<TradeStoreEntity> trade = getTradeStoreEntity(tradeStore.getTradeId());
		TradeStoreEntity tradeStoreEntity = null;
		if (trade.isPresent()) {
			tradeStoreEntity = trade.get();
			if (tradeStoreEntity.getBookIdMaturityDate().compareTo(new java.util.Date()) < 0) {
				throw new RecordNotFoundException("Trade has expired");
			}
			if (tradeStoreEntity.getVersion() > tradeStore.getVersion()) {
				throw new RecordNotFoundException("Old Version Trade not Allowed");
			}
			tradeStoreEntity.setVersion(tradeStore.getVersion());
			tradeStoreEntity.setBookId(tradeStore.getBookId());
			tradeStoreEntity.setBookIdMaturityDate(tradeStore.getBookIdMaturityDate());
			tradeStoreEntity.setCounterPartyId(tradeStore.getCounterPartyId());
		} else {
			tradeStoreEntity = new TradeStoreEntity();
			tradeStoreEntity.setTradeId(tradeStore.getTradeId());
			tradeStoreEntity.setVersion(tradeStore.getVersion());
			tradeStoreEntity.setBookId(tradeStore.getBookId());
			tradeStoreEntity.setBookIdMaturityDate(tradeStore.getBookIdMaturityDate());
			tradeStoreEntity.setCounterPartyId(tradeStore.getCounterPartyId());
			tradeStoreEntity.setCreatedDate(new Date(new java.util.Date().getTime()));
		}
		return tradeRepo.save(tradeStoreEntity);
	}

	public List<TradeStoreEntity> markExpired() {
		List<TradeStoreEntity> tradeList = tradeRepo.findByBookIdMaturityDateBeforeAndExpired(new Date(new java.util.Date().getTime()), "N");
		if (!CollectionUtils.isEmpty(tradeList)) {
			tradeList.forEach(e -> e.setExpired("Y"));
		}
		return tradeRepo.saveAll(tradeList);
	}

	private Optional<TradeStoreEntity> getTradeStoreEntity(String tradeId) {
		Optional<List<TradeStoreEntity>> trade = tradeRepo.findByTradeStoreIdentityTradeIdIgnoreCase(tradeId);

		Optional<TradeStoreEntity> tradeStoreEntity = Optional.empty();
		if (trade.isPresent()) {
			tradeStoreEntity = trade.get().stream().max(Comparator.comparing(TradeStoreEntity::getVersion));
		}
		return tradeStoreEntity;
	}
}