package com.dboi.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dboi.entity.TradeStoreEntity;
import com.dboi.entity.TradeStoreIdentity;
 
@Repository
public interface TradeStoreRepository
        extends JpaRepository<TradeStoreEntity, TradeStoreIdentity> {
	
	Optional<List<TradeStoreEntity>> findByTradeStoreIdentityTradeIdIgnoreCase(String tradeId);
	
	List<TradeStoreEntity> findByBookIdMaturityDateBeforeAndExpired(Date bookIdMaturityDate, String expired);

}
