package com.bank.loyalty.service.jpa.repo;

import org.springframework.data.repository.CrudRepository;

import com.bank.loyalty.service.jpa.entity.GainPointsTransaction;

public interface GainPointsTransactionRepo extends CrudRepository<GainPointsTransaction, Long> {

	boolean existsByOriginalId(long originalId);
	
}
