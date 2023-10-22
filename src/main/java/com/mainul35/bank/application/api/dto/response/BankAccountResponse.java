package com.mainul35.bank.application.api.dto.response;

import com.mainul35.bank.application.api.dto.request.BankAccountRequest;
import com.mainul35.bank.domain.entity.Customer;

import java.math.BigDecimal;

public record BankAccountResponse (Long id, CustomerResponse customer, BigDecimal balance, String accountNumber){
    public BankAccountRequest toRequest () {
        return new BankAccountRequest(this.customer.toRequest(), this.balance, this.accountNumber);
    }


}
