package com.bank.loyalty.service.jpa.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class GainPointsTransaction extends Transaction {
	
	private BigDecimal moneyAmount;
	
	@Column(unique = true)
	private long originalId;

	public long getOriginalId() {
		return originalId;
	}

	public void setOriginalId(long originalId) {
		this.originalId = originalId;
	}

	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	
}
