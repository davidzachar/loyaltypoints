package com.bank.loyalty.service.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class MoneyToPointsConverterTest {

	@Test
	public void testNull() {
		assertThrows(NullPointerException.class, () -> MoneyToPointsConverter.convert(null));
	}
	
	@Test
	public void testZeroAndInvalidValues() {
		assertEquals(BigDecimal.ZERO, MoneyToPointsConverter.convert(new BigDecimal("0")));
		assertEquals(BigDecimal.ZERO, MoneyToPointsConverter.convert(new BigDecimal("-1000")));
	}
	
	@Test
	public void testValidValues() {
		assertEquals(new BigDecimal(4500), MoneyToPointsConverter.convert(new BigDecimal(4500)));
		assertEquals(new BigDecimal(10900), MoneyToPointsConverter.convert(new BigDecimal(7800)));
		assertEquals(new BigDecimal(10), MoneyToPointsConverter.convert(new BigDecimal("10.99")));
		assertEquals(new BigDecimal(5002), MoneyToPointsConverter.convert(new BigDecimal(5001)));
		assertEquals(new BigDecimal(5000), MoneyToPointsConverter.convert(new BigDecimal("5000.99")));
		assertEquals(new BigDecimal(10000), MoneyToPointsConverter.convert(new BigDecimal("7500.99")));
		assertEquals(new BigDecimal(10003), MoneyToPointsConverter.convert(new BigDecimal("7501.99")));
	}
	
	
}
