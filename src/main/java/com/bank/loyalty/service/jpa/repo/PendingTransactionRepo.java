package com.bank.loyalty.service.jpa.repo;

import org.springframework.data.repository.CrudRepository;

import com.bank.loyalty.service.jpa.entity.PendingTransaction;

public interface PendingTransactionRepo extends CrudRepository<PendingTransaction, Long> {

}
