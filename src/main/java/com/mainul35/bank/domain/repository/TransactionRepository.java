package com.mainul35.bank.domain.repository;

import com.mainul35.bank.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTxnReference(String txnRef);
}
