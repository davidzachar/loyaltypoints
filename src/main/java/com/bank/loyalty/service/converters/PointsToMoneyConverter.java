package com.bank.loyalty.service.converters;

import java.math.BigDecimal;

public class PointsToMoneyConverter {

	private static final BigDecimal HUNDRED = new BigDecimal(100);
	
	public static BigDecimal convert(BigDecimal moneyAmount) {
		return HUNDRED.multiply(moneyAmount);
	}
	
}
