package com.mainul35.bank.application.api.controllers;

import com.mainul35.bank.application.api.dto.request.TransferRequest;
import com.mainul35.bank.application.api.dto.response.TransactionHistoryResponse;
import com.mainul35.bank.application.services.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionHistory {

    private final ITransactionService transactionService;

    public TransactionHistory(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionHistoryResponse>> transferHistory (@PathVariable("accountNumber") String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactionByAccountNumber(accountNumber));
    }
}
