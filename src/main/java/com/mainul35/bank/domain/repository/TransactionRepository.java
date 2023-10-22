package com.mainul35.bank.domain.repository;

import com.mainul35.bank.application.api.dto.response.TransactionHistoryResponse;
import com.mainul35.bank.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTxnReference(String txnRef);
    List<Transaction> findAllByAccount_AccountNumber(String accountNumber);

    @Query("""
            SELECT NEW com.mainul35.bank.application.api.dto.response.TransactionHistoryResponse(
            t1.account.accountNumber, t2.account.accountNumber, t1.amount, t1.newBalance, t1.txnType, t1.txnReference, t1.createdAt ) 
            FROM com.mainul35.bank.domain.entity.Transaction t1 
            FULL JOIN com.mainul35.bank.domain.entity.Transaction t2 
            ON t1.txnReference = t2.txnReference 
            WHERE t1.txnType = 'TRANSFER' 
            AND t1.account.id != t2.account.id 
            AND t1.txnReference = :txnReference
            AND t1.createdAt = (SELECT MIN(t.createdAt) FROM com.mainul35.bank.domain.entity.Transaction t WHERE t.txnReference = t1.txnReference)
            """)
    TransactionHistoryResponse findTransactionOfTypeTransfer(@Param("txnReference") String txnReference);
}
