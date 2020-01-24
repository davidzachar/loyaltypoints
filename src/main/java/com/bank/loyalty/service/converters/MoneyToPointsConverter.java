package com.bank.loyalty.service.converters;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyToPointsConverter {
	
	private static final BigDecimal FIRST = new BigDecimal(5000);
	private static final BigDecimal SECOND = new BigDecimal(7500);

	public static BigDecimal convert(BigDecimal moneyAmount) {
		if (BigDecimal.ZERO.compareTo(moneyAmount) >= 0) {
			return BigDecimal.ZERO;
		}
		BigDecimal count = new BigDecimal(0);
		BigDecimal remainder = moneyAmount.setScale(0, RoundingMode.DOWN);
	    if (SECOND.compareTo(moneyAmount) <= 0) {
	    	count = count.add(remainder.subtract(SECOND).multiply(new BigDecimal(3)));
	    	remainder = SECOND;
	    }
		if (FIRST.compareTo(moneyAmount) <= 0) {
			count = count.add(remainder.subtract(FIRST).multiply(new BigDecimal(2)));
	    	remainder = FIRST;
		}
		return count.add(remainder);
	}

}
