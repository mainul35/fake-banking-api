package com.mainul35.bank.application.services;

import com.mainul35.bank.application.api.dto.request.BankAccountRequest;

public interface IBankAccountService {

    <T> T createBankAccount (BankAccountRequest bankAccountRequest);

    <T> T findAccountByAccountNumber(String accountNumber);
}
