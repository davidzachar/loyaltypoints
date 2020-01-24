package com.bank.loyalty.service.jpa.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class SpendPointsTransaction extends Transaction {
	
	private BigDecimal moneyAmount;

	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	
}
