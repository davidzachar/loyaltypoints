package com.bank.loyalty.service.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bank.loyalty.service.jpa.entity.GainPointsTransaction;
import com.bank.loyalty.service.jpa.entity.LoyaltyAccount;
import com.bank.loyalty.service.jpa.entity.PendingTransaction;
import com.bank.loyalty.service.jpa.entity.Transaction;

public class PendingToActivePointsConverter {

	private static final BigDecimal THRESHOLD = new BigDecimal(500);

	public static List<Transaction> convert(LoyaltyAccount account) {
		if (THRESHOLD.compareTo(account.getPendingPoints()) > 0) {
			return convertPendingToZeroActivePoints(account);
		} else {
			long daysOfWeekCovered = account.getPending().stream().map(p -> p.getRegisteredAt().getDayOfWeek()).distinct().count();
			if (daysOfWeekCovered == 7L) {
				return convertPendingToActivePoints(account);
			} else {
				return convertPendingToZeroActivePoints(account);
			}
		}
	}
	
	private static List<Transaction> convertPendingToActivePoints(LoyaltyAccount account) {
		List<Transaction> l = new ArrayList<>();
		for (PendingTransaction p : account.getPending()) {
			account.setCurrentPoints(account.getCurrentPoints().add(p.getPointAmount()));
			Transaction t = convertTransaction(p);
			l.add(t);
		}
		account.setPendingPoints(BigDecimal.ZERO);
		return l;
	}
	
	private static List<Transaction> convertPendingToZeroActivePoints(LoyaltyAccount account) {
		List<Transaction> l = new ArrayList<>();
		for (PendingTransaction p : account.getPending()) {
			p.setPointAmount(BigDecimal.ZERO);
			Transaction t = convertTransaction(p);
			l.add(t);
		}
		account.setPendingPoints(BigDecimal.ZERO);
		account.setPending(new ArrayList<>());
		return l;
		
	}
	
	private static GainPointsTransaction convertTransaction(PendingTransaction p) {
		GainPointsTransaction t = new GainPointsTransaction();
		t.setLoyaltyAccount(p.getLoyaltyAccount());
		t.setPointAmount(p.getPointAmount());
		t.setOriginalId(p.getId());
		t.setMoneyAmount(p.getMoneyAmount());
		t.setRegisteredAt(p.getRegisteredAt());
		return t;
	}

}
