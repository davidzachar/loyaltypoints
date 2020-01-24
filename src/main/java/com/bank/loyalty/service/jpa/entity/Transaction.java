package com.bank.loyalty.service.jpa.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public abstract class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "accountId")
	private LoyaltyAccount loyaltyAccount;
	private LocalDateTime registeredAt;

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
	public BigDecimal getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}
	
	
}
