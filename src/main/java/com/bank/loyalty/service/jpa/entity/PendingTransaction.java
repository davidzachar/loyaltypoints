package com.bank.loyalty.service.jpa.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PendingTransaction {
	@Id
	private long id;
	@ManyToOne
	@JoinColumn(name = "accountId")
	private LoyaltyAccount loyaltyAccount;
	private LocalDateTime registeredAt;
	private BigDecimal moneyAmount;
	private BigDecimal pointAmount;
	
	public long getId() {
		return id;
	}
	public LocalDateTime getRegisteredAt() {
		return registeredAt;
	}
	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}
	public LoyaltyAccount getLoyaltyAccount() {
		return loyaltyAccount;
	}
	public void setLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
		this.loyaltyAccount = loyaltyAccount;
	}
	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}
	public BigDecimal getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	
}
