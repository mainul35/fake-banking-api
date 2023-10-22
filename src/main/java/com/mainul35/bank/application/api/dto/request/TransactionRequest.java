package com.mainul35.bank.application.api.dto.request;

import com.mainul35.bank.domain.entity.BankAccount;
import com.mainul35.bank.domain.entity.Transaction;
import com.mainul35.bank.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionRequest (
    BankAccountRequest account,
    BigDecimal amount,
    TransactionType txnType
){
    public Transaction toEntity () {
        return Transaction.builder()
                .account(account.toEntity())
                .amount(amount)
                .txnType(txnType)
                .build();
    }
}
