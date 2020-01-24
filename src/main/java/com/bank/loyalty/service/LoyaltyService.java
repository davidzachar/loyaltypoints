package com.bank.loyalty.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bank.loyalty.api.entity.RequestTransaction;
import com.bank.loyalty.service.converters.LoseAllPointsCondition;
import com.bank.loyalty.service.converters.MoneyToPointsConverter;
import com.bank.loyalty.service.converters.PendingToActivePointsConverter;
import com.bank.loyalty.service.converters.PointsToMoneyConverter;
import com.bank.loyalty.service.jpa.entity.LoseAllPoints;
import com.bank.loyalty.service.jpa.entity.LoyaltyAccount;
import com.bank.loyalty.service.jpa.entity.PendingTransaction;
import com.bank.loyalty.service.jpa.entity.SpendPointsTransaction;
import com.bank.loyalty.service.jpa.entity.Transaction;
import com.bank.loyalty.service.jpa.repo.GainPointsTransactionRepo;
import com.bank.loyalty.service.jpa.repo.LoyaltyAccountRepo;
import com.bank.loyalty.service.jpa.repo.PendingTransactionRepo;
import com.bank.loyalty.service.jpa.repo.TransactionRepo;

@Service
public class LoyaltyService {
	
	@Autowired
	LoyaltyAccountRepo loyaltyAccountRepo;
	
	@Autowired
	PendingTransactionRepo pendingTransactionRepo;
	
	@Autowired
	TransactionRepo transactionRepo;
	
	@Autowired
	GainPointsTransactionRepo gainPointsTransactionRepo;

	public LoyaltyAccount getLoyaltyAccount(String userId) {
		return validate(loyaltyAccountRepo.findByUserId(userId));
	}
	
	@Scheduled(cron = "0 0 1 * * MON")
	public void processPending() {
		for (LoyaltyAccount la : loyaltyAccountRepo.findAll()) {
			List<Transaction> newTransactions = PendingToActivePointsConverter.convert(la);
			transactionRepo.saveAll(newTransactions);
			pendingTransactionRepo.deleteAll(la.getPending());
			la.setPending(new ArrayList<>());
			loyaltyAccountRepo.save(la);
		}
	}
	
	public BigDecimal spend(LoyaltyAccount la, BigDecimal moneyAmount) {
		BigDecimal pointsAmount = PointsToMoneyConverter.convert(moneyAmount);
		if (pointsAmount.compareTo(la.getCurrentPoints()) > 0) {
			throw new InvalidParameterException("Not enough points on account for the transaction");
		}
		SpendPointsTransaction t = new SpendPointsTransaction();
		t.setMoneyAmount(moneyAmount);
		t.setRegisteredAt(LocalDateTime.now());
		t.setPointAmount(pointsAmount);
		t.setLoyaltyAccount(la);
		la.setCurrentPoints(la.getCurrentPoints().subtract(pointsAmount));
		transactionRepo.save(t);
		loyaltyAccountRepo.save(la);
		return pointsAmount;
	}
	
	public BigDecimal registerTransaction(LoyaltyAccount la, RequestTransaction rt) {
		validate(rt);
		PendingTransaction pt = convertToPending(rt);
		pt.setLoyaltyAccount(la);
		la.getPending().add(pt);
		la.setPendingPoints(la.getPendingPoints().add(pt.getPointAmount()));
		la.setLastTransactionDate(pt.getRegisteredAt());
		pendingTransactionRepo.save(pt);
		loyaltyAccountRepo.save(la);
		return pt.getPointAmount();
	}
	
	private void validate(RequestTransaction rt) {
		if (pendingTransactionRepo.existsById(rt.getId()) || gainPointsTransactionRepo.existsByOriginalId(rt.getId())) {
			throw new InvalidParameterException("Transaction with given id already exist");
		}
	}
	
	private PendingTransaction convertToPending(RequestTransaction rt) {
		PendingTransaction pt = new PendingTransaction();
		pt.setId(rt.getId());
		pt.setMoneyAmount(rt.getAmount());
		pt.setPointAmount(MoneyToPointsConverter.convert(rt.getAmount()));
		pt.setRegisteredAt(LocalDateTime.now());
		return pt;
	}
	
	private LoyaltyAccount validate(LoyaltyAccount la) {
		if (LoseAllPointsCondition.is(la)) {
			BigDecimal allPoints = la.getCurrentPoints();
			LoseAllPoints losePoints = new LoseAllPoints();
			losePoints.setLoyaltyAccount(la);
			losePoints.setPointAmount(allPoints);
			losePoints.setRegisteredAt(LocalDateTime.now());
			la.setCurrentPoints(BigDecimal.ZERO);
			transactionRepo.save(losePoints);
			la.getHistory().add(losePoints);
			return loyaltyAccountRepo.save(la);
		}
		return la;
	}
	
}
