package com.mainul35.bank.application.services;

import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.application.api.dto.response.TransactionHistoryResponse;
import com.mainul35.bank.application.api.dto.response.TransactionResponse;
import com.mainul35.bank.enums.TransactionType;

import java.util.List;
import java.util.Optional;

public interface ITransactionService {
    String saveTransaction(TransactionRequest txnRequest);
    TransactionResponse getTransaction(String txnRef, TransactionType transactionType);
    List<TransactionHistoryResponse> getTransactionByAccountNumber(String accountNumber);
}
