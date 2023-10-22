package com.mainul35.bank.application.api.dto.request;

import com.mainul35.bank.domain.entity.BankAccount;

import java.math.BigDecimal;

public record BankAccountRequest (CustomerRequest customerRequest, BigDecimal balance){
    public BankAccount toEntity() {
        return BankAccount.builder().customer(customerRequest.toEntity()).balance(balance).build();
    }
}
