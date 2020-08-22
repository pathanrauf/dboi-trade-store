package com.dboi.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TRADE_STORE")
public class TradeStoreEntity implements Serializable
{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TradeStoreIdentity tradeStoreIdentity;

	@Column(name = "COUNTER_PARTY_ID")
	private String counterPartyId;

	@Column(name = "BOOK_ID")
	private String bookId;

	@Column(name = "BOOK_ID_MATURITY_DATE")
	private Date bookIdMaturityDate;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "EXPIRED")
	private String expired = "N";

	public TradeStoreEntity() {

	}

	public TradeStoreEntity(String tradeId, int version, String counterPartyId, String bookId, Date bookIdMaturityDate, Date createdDate, String expired) {
		this.tradeStoreIdentity = new TradeStoreIdentity(tradeId, version);
		this.counterPartyId = counterPartyId;
		this.bookId = bookId;
		this.bookIdMaturityDate = bookIdMaturityDate;
		this.createdDate = createdDate;
		this.expired = expired;
	}

	public TradeStoreEntity(TradeStoreIdentity tradeStoreEntityIdentity, String counterPartyId, String bookId, Date bookIdMaturityDate, Date createdDate,
			String expired) {
		this.tradeStoreIdentity = tradeStoreEntityIdentity;
		this.counterPartyId = counterPartyId;
		this.bookId = bookId;
		this.bookIdMaturityDate = bookIdMaturityDate;
		this.createdDate = createdDate;
		this.expired = expired;
	}

	public String getTradeId() {
		return tradeStoreIdentity.getTradeId();
	}

	public void setTradeId(String tradeId) {
		if (null == tradeStoreIdentity) {
			tradeStoreIdentity = new TradeStoreIdentity();
		}
		this.tradeStoreIdentity.setTradeId(tradeId);
	}

	public int getVersion() {
		return tradeStoreIdentity.getVersion();
	}

	public TradeStoreIdentity getTradeStoreIdentity() {
		return tradeStoreIdentity;
	}

	public void setTradeStoreIdentity(TradeStoreIdentity tradeStoreIdentity) {
		this.tradeStoreIdentity = tradeStoreIdentity;
	}

	public void setVersion(int version) {
		if (null == tradeStoreIdentity) {
			tradeStoreIdentity = new TradeStoreIdentity();
		}
		this.tradeStoreIdentity.setVersion(version);
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public Date getBookIdMaturityDate() {
		return bookIdMaturityDate;
	}

	public void setBookIdMaturityDate(Date bookIdMaturityDate) {
		this.bookIdMaturityDate = bookIdMaturityDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "TradeStoreEntity [tradeStoreEntityIdentity=" + tradeStoreIdentity + ", counterPartyId=" + counterPartyId + ", bookId=" + bookId + ", bookIdMaturityDate="
				+ bookIdMaturityDate + ", createdDate=" + createdDate + ", expired=" + expired + "]";
	}

}