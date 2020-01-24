package com.bank.loyalty.service.jpa.repo;

import org.springframework.data.repository.CrudRepository;

import com.bank.loyalty.service.jpa.entity.LoyaltyAccount;

public interface LoyaltyAccountRepo extends CrudRepository<LoyaltyAccount, Long> {

	LoyaltyAccount findByUserId(String userId);
	
}
