package com.bank.loyalty.service.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.bank.loyalty.service.jpa.entity.LoyaltyAccount;

public class LoseAllPointsConditionTest {

	@Test
	public void testCondition() {
		LoyaltyAccount la = new LoyaltyAccount();
		la.setCurrentPoints(BigDecimal.ZERO);
		assertEquals(false, LoseAllPointsCondition.is(la));
		
		la.setCurrentPoints(BigDecimal.ONE);
		la.setLastTransactionDate(LocalDateTime.now());
		assertEquals(false, LoseAllPointsCondition.is(la));
		
		la.setLastTransactionDate(LocalDateTime.now().minusWeeks(4).minusDays(6));
		assertEquals(false, LoseAllPointsCondition.is(la));
		
		la.setLastTransactionDate(LocalDateTime.now().minusWeeks(5).minusSeconds(1));
		assertEquals(true, LoseAllPointsCondition.is(la));
		
		
	}
	
}
