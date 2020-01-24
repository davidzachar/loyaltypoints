package com.bank.loyalty.service.jpa.repo;

import org.springframework.data.repository.CrudRepository;

import com.bank.loyalty.service.jpa.entity.Transaction;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
	
}
