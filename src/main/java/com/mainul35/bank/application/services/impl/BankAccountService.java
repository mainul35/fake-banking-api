package com.mainul35.bank.application.services.impl;

import com.mainul35.bank.application.api.dto.request.BankAccountRequest;
import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import com.mainul35.bank.application.services.IBankAccountService;
import com.mainul35.bank.domain.repository.BankAccountRepository;
import com.mainul35.bank.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService implements IBankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private static final String ACCOUNT_NUMBER_PREFIX = "123456000";

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public String createBankAccount(BankAccountRequest bankAccountRequest) {
        var account = bankAccountRequest.toEntity();
        account.setAccountNumber(ACCOUNT_NUMBER_PREFIX + (bankAccountRepository.count() + 1));
        account = bankAccountRepository.save(account);
        return account.getAccountNumber();
    }

    public BankAccountResponse findAccountByAccountNumber(String accountNumber) {
        var accountOptional = bankAccountRepository.findByAccountNumber(accountNumber);
        var account = accountOptional.orElseThrow(() -> new NotFoundException("Bank account not found for this Account Number"));
        return account.toResponse();
    }
}
