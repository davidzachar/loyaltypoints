package com.bank.loyalty.service.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import com.bank.loyalty.service.jpa.entity.LoyaltyAccount;
import com.bank.loyalty.service.jpa.entity.PendingTransaction;

public class PendingToActivePointsConverterTest {
	
	@Test
	public void testLowAmountOfTransactions() {
		LoyaltyAccount la = new LoyaltyAccount();
		for (int i = 0; i < 7; i++) {
			PendingTransaction t = new PendingTransaction();
			t.setPointAmount(new BigDecimal(50));
			la.getPending().add(t);
			la.setPendingPoints(la.getPendingPoints().add(new BigDecimal(50)));
		}
		PendingToActivePointsConverter.convert(la);
		assertEquals(BigDecimal.ZERO, la.getPendingPoints());
		assertEquals(BigDecimal.ZERO, la.getCurrentPoints());
	}
	
	@Test
	public void oneDaysOfWeekMissingTransaction() {
		LoyaltyAccount la = new LoyaltyAccount();
		for (int i = 0; i < 6; i++) {
			PendingTransaction t = new PendingTransaction();
			t.setPointAmount(new BigDecimal(200));
			la.getPending().add(t);
			la.setPendingPoints(la.getPendingPoints().add(new BigDecimal(200)));
			t.setRegisteredAt(LocalDateTime.of(2020, Month.JANUARY, 6 + i, 5, 10));
		}
		PendingToActivePointsConverter.convert(la);
		assertEquals(BigDecimal.ZERO, la.getPendingPoints());
		assertEquals(BigDecimal.ZERO, la.getCurrentPoints());
	}
	
	@Test
	public void testValidCase() {
		LoyaltyAccount la = new LoyaltyAccount();
		for (int i = 0; i < 7; i++) {
			PendingTransaction t = new PendingTransaction();
			t.setPointAmount(new BigDecimal(200));
			la.getPending().add(t);
			la.setPendingPoints(la.getPendingPoints().add(new BigDecimal(200)));
			t.setRegisteredAt(LocalDateTime.of(2020, Month.JANUARY, 6 + i, 5, 10));
		}
		PendingToActivePointsConverter.convert(la);
		assertEquals(BigDecimal.ZERO, la.getPendingPoints());
		assertEquals(new BigDecimal(1400), la.getCurrentPoints());
	}

}
