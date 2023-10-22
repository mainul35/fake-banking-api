package com.mainul35.bank.application.api.dto.request;

import com.mainul35.bank.domain.entity.BankAccount;
import com.mainul35.bank.domain.entity.Transaction;
import com.mainul35.bank.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest (
    BankAccountRequest account,
    BigDecimal amount,
    TransactionType txnType,
    String txnRef
){
    public Transaction toEntity () {
        var txRef = txnRef;
        if (txnRef == null) {
            txRef = UUID.randomUUID().toString();
        }
        return Transaction.builder()
                .account(account.toEntity())
                .amount(amount)
                .txnType(txnType)
                .txnReference(txRef)
                .build();
    }
}
