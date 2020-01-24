package com.bank.loyalty.service.jpa.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class LoyaltyAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long accountId;
	@Column(unique = true)
	private String userId;
	private BigDecimal currentPoints = BigDecimal.ZERO;
	private BigDecimal pendingPoints = BigDecimal.ZERO;
	private LocalDateTime lastTransactionDate;
	
	@OneToMany(targetEntity = PendingTransaction.class, mappedBy = "loyaltyAccount", fetch = FetchType.EAGER)
	private List<PendingTransaction> pending = new ArrayList<>();
	@OneToMany(targetEntity = Transaction.class, mappedBy = "loyaltyAccount", fetch = FetchType.LAZY)
	private List<Transaction> history = new ArrayList<>();

	public long getAccountId() {
		return accountId;
	}
	public String getUserId() {
		return userId;
	}
	public BigDecimal getCurrentPoints() {
		return currentPoints;
	}
	public BigDecimal getPendingPoints() {
		return pendingPoints;
	}
	public List<PendingTransaction> getPending() {
		return pending;
	}
	public List<Transaction> getHistory() {
		return history;
	}
	public void setCurrentPoints(BigDecimal currentPoints) {
		this.currentPoints = currentPoints;
	}
	public void setPendingPoints(BigDecimal pendingPoints) {
		this.pendingPoints = pendingPoints;
	}
	public void setPending(List<PendingTransaction> pending) {
		this.pending = pending;
	}
	public void setHistory(List<Transaction> history) {
		this.history = history;
	}
	public LocalDateTime getLastTransactionDate() {
		return lastTransactionDate;
	}
	public void setLastTransactionDate(LocalDateTime lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}
}
