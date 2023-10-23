package com.mainul35.bank.application.api.dto.response;

import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.domain.entity.BankAccount;
import com.mainul35.bank.enums.TransactionType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionResponse (
        Long id,
        BankAccountResponse account,
        BigDecimal amount,

        BigDecimal newBalance,
        TransactionType txnType,
        String txnRef,
        OffsetDateTime createdAt
) {

    public TransactionRequest toRequest () {
        return new TransactionRequest(account.toRequest(), amount, newBalance, txnType, txnRef);
    }
}
