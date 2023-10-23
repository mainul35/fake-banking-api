package com.mainul35.bank.application.services.impl;

import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.application.api.dto.response.TransactionHistoryResponse;
import com.mainul35.bank.application.api.dto.response.TransactionResponse;
import com.mainul35.bank.application.services.ITransactionService;
import com.mainul35.bank.domain.repository.BankAccountRepository;
import com.mainul35.bank.domain.repository.TransactionRepository;
import com.mainul35.bank.enums.TransactionType;
import com.mainul35.bank.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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

    public List<TransactionHistoryResponse> getTransactionByAccountNumber(String accountNumber) {
        var txList = this.transactionRepository.findAllByAccount_AccountNumber(accountNumber);
        return txList.stream().map(transaction -> {
            var historyItem = new TransactionHistoryResponse();
            if (transaction.getTxnType().equals(TransactionType.TRANSFER)) {
                historyItem = transactionRepository.findTransactionOfTypeTransfer(transaction.getTxnReference());
            } else if (transaction.getTxnType().equals(TransactionType.DEPOSIT)){
                historyItem.setToAccount(transaction.getAccount().getAccountNumber());
                historyItem.setAmount(transaction.getAmount());
                historyItem.setNewBalance(transaction.getNewBalance());
                historyItem.setTxnType(transaction.getTxnType());
                historyItem.setTxnRef(transaction.getTxnReference());
                historyItem.setCreatedAt(transaction.getCreatedAt());
            } else if (transaction.getTxnType().equals(TransactionType.WITHDRAW)) {
                historyItem.setFromAccount(transaction.getAccount().getAccountNumber());
                historyItem.setAmount(transaction.getAmount());
                historyItem.setNewBalance(transaction.getNewBalance());
                historyItem.setTxnType(transaction.getTxnType());
                historyItem.setTxnRef(transaction.getTxnReference());
                historyItem.setCreatedAt(transaction.getCreatedAt());
            }
            return historyItem;
        }).toList();
    }
}
