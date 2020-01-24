package com.bank.loyalty.api.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class RequestTransaction {
	
	@Positive
	private long id;
	@NotBlank
	private String userId;
	@Positive
	private BigDecimal amount;

	public long getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	
}
