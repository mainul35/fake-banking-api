package com.mainul35.bank.application.services;

import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.application.api.dto.response.TransactionResponse;

import java.util.Optional;

public interface ITransactionService {
    String saveTransaction(TransactionRequest txnRequest);
    TransactionResponse getTransaction(String txnRef);
}
