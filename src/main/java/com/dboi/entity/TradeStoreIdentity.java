package com.dboi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TradeStoreIdentity implements Serializable
{
	@Column(name = "TRADE_ID")
	private String tradeId;

	@Column(name = "VERSION")
	private int version;

	public TradeStoreIdentity() {

	}

	public TradeStoreIdentity(String tradeId) {
		super();
		this.tradeId = tradeId;
	}

	public TradeStoreIdentity(String tradeId, int version) {
		super();
		this.tradeId = tradeId;
		this.version = version;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tradeId == null) ? 0 : tradeId.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeStoreIdentity other = (TradeStoreIdentity) obj;
		if (tradeId == null) {
			if (other.tradeId != null)
				return false;
		} else if (!tradeId.equals(other.tradeId))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TradeStoreEntityIdentity [tradeId=" + tradeId + ", version=" + version + "]";
	}
}