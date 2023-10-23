package com.mainul35.bank.application.services;

import com.mainul35.bank.application.api.dto.request.BankAccountRequest;
import com.mainul35.bank.application.api.dto.response.BankAccountResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IBankAccountService {

    String createBankAccountForExistingCustomer (BankAccountRequest bankAccountRequest);

    BankAccountResponse findAccountByAccountNumber(String accountNumber);

    List<BankAccountResponse> findBankAccountsOfCustomerByCustomerEmail (String email);

    <T> T addMoneyToAccount(BankAccountRequest bankAccountRequest, BigDecimal moneyToAdd);

    <T> T withdrawMoneyFromAccount(BankAccountRequest accountReq, BigDecimal balanceToDeduct);

    String createCustomerAndAccount(BankAccountRequest bankAccountRequest);
}
