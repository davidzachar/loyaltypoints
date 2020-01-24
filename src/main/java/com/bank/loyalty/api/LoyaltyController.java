package com.bank.loyalty.api;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.loyalty.api.entity.RequestTransaction;
import com.bank.loyalty.service.LoyaltyService;
import com.bank.loyalty.service.jpa.entity.LoyaltyAccount;
import com.bank.loyalty.service.jpa.entity.Transaction;

@RestController
@Validated
public class LoyaltyController {
	
	@Autowired
	LoyaltyService loyaltyService;
	
	@PostMapping("/transaction")
	public String addTransaction(@Valid @RequestBody RequestTransaction t) {
		LoyaltyAccount la = loyaltyService.getLoyaltyAccount(t.getUserId());
		validate(la);
		BigDecimal pointsAdded = loyaltyService.registerTransaction(la, t);
		return "Transaction registered and " + pointsAdded + " points are now added to the pending amount.";
	}
	
	@GetMapping("/points")
	public BigDecimal getAvailablePoints(@RequestParam String userId) {
		LoyaltyAccount la = loyaltyService.getLoyaltyAccount(userId);
		validate(la);
		return la.getCurrentPoints();
	}
	
	@GetMapping("/pendingPoints")
	public BigDecimal getPendingPoints(@RequestParam String userId) {
		LoyaltyAccount la = loyaltyService.getLoyaltyAccount(userId);
		validate(la);
		return la.getPendingPoints();
	}
	
	@PostMapping("/spend")
	public String spendPoints(@RequestParam String userId, @Valid @Positive @RequestParam BigDecimal moneyAmount) throws OperationNotSupportedException {
		LoyaltyAccount la = loyaltyService.getLoyaltyAccount(userId);
		validate(la);
		BigDecimal points = loyaltyService.spend(la, moneyAmount);
		return points + " points successfully transfered to " + moneyAmount + "EUR";
	}
	
	@GetMapping("/history")
	public List<Transaction> showHistory(@RequestParam String userId) {
		LoyaltyAccount la = loyaltyService.getLoyaltyAccount(userId);
		validate(la);
		return la.getHistory();
	}
	
	private void validate(LoyaltyAccount la) {
		if (la == null) {
			throw new InvalidParameterException("Account not found");
		}
	}

}
