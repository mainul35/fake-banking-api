package com.mainul35.bank.application.api.dto.request;

import com.mainul35.bank.domain.entity.BankAccount;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@NotNull
public record BankAccountRequest (
        @NotNull CustomerRequest customerRequest,
        BigDecimal balance,
        String accountNumber
){

    public BankAccountRequest customerRequest(CustomerRequest customerRequest) {
        return new BankAccountRequest(customerRequest, balance, accountNumber);
    }
    public BankAccount toEntity() {
        return BankAccount.builder()
                .customer(customerRequest.toEntity())
                .balance(balance)
                .accountNumber(accountNumber).build();
    }
}
