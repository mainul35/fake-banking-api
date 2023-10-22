package com.mainul35.bank.application.api.dto.response;

import com.mainul35.bank.domain.entity.Customer;

import java.math.BigDecimal;

public record BankAccountResponse (Long id, Customer customer, BigDecimal balance, String accountNumber){
}
