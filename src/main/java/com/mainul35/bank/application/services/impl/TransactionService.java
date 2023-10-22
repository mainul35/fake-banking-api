package com.mainul35.bank.application.services.impl;

import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.application.api.dto.response.TransactionResponse;
import com.mainul35.bank.application.services.ITransactionService;
import com.mainul35.bank.domain.repository.BankAccountRepository;
import com.mainul35.bank.domain.repository.TransactionRepository;
import com.mainul35.bank.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    private final BankAccountRepository bankAccountRepository;

    public TransactionService(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public String saveTransaction(TransactionRequest txnRequest) {
        var txn = txnRequest.toEntity();
        var account = bankAccountRepository.findByAccountNumber(txn.getAccount().getAccountNumber())
                .orElseThrow(() -> new NotFoundException("Account not found for this transaction"));
        txn.setAccount(account);
        txn.setCreatedAt(OffsetDateTime.now());
        txn = this.transactionRepository.save(txn);
        return txn.getTxnReference();
    }

    @Override
    public TransactionResponse getTransaction(String txnRef) {
        var txn = this.transactionRepository.findByTxnReference(txnRef)
                .orElseThrow(() -> new NotFoundException("No transaction found with this txn ref no.: ".concat(txnRef)));
        return txn.toResponse();
    }
}
