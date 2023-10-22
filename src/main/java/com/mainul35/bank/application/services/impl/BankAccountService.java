package com.mainul35.bank.application.services.impl;

import com.mainul35.bank.application.api.dto.request.BankAccountRequest;
import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import com.mainul35.bank.application.services.IBankAccountService;
import com.mainul35.bank.domain.entity.BankAccount;
import com.mainul35.bank.domain.repository.BankAccountRepository;
import com.mainul35.bank.exceptions.NotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

    public List<BankAccountResponse> findBankAccountsOfCustomerByCustomerEmail (String email) {
        var accounts = bankAccountRepository.findBankAccountByCustomer_Email(email);
        if (accounts.isEmpty()) {
            throw new NotFoundException("No customer account found with this email address");
        }
        return accounts.stream().map(BankAccount::toResponse).toList();
    }

    @Override
    public BankAccountResponse withdrawMoneyFromAccount(BankAccountRequest accountReq, BigDecimal balanceToDeduct) {
        var account = this.bankAccountRepository.findByAccountNumber(accountReq.toEntity().getAccountNumber())
                .orElseThrow(() -> new NotFoundException("No Bank account found with this account number: ".concat(accountReq.toEntity().getAccountNumber())));
        if (balanceToDeduct == null) throw new ServiceException("Balance cannot be null");
        balanceToDeduct = balanceToDeduct.setScale(2, RoundingMode.HALF_EVEN);
        account.deductMoney(balanceToDeduct);
        account = this.bankAccountRepository.save(account);
        return account.toResponse();
    }

    @Override
    public BankAccountResponse addMoneyToAccount(BankAccountRequest bankAccountRequest, BigDecimal moneyToAdd) {
        var account = this.bankAccountRepository.findByAccountNumber(bankAccountRequest.toEntity().getAccountNumber())
                .orElseThrow(() -> new NotFoundException("No Bank account found with this account number: ".concat(bankAccountRequest.toEntity().getAccountNumber())));
        if (moneyToAdd == null) throw new ServiceException("Balance cannot be null");
        moneyToAdd = moneyToAdd.setScale(2, RoundingMode.HALF_EVEN);
        account.addMoney(moneyToAdd);
        account = this.bankAccountRepository.save(account);
        return account.toResponse();
    }
}
