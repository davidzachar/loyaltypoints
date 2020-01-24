package com.bank.loyalty.service.converters;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.loyalty.service.jpa.entity.LoyaltyAccount;

public class LoseAllPointsCondition {
	
	public static boolean is(LoyaltyAccount la) {
		return BigDecimal.ZERO.compareTo(la.getCurrentPoints()) < 0
				&& LocalDateTime.now().minusWeeks(5).compareTo(la.getLastTransactionDate()) > 0;
	}

}
