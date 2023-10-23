package com.mainul35.bank.application.api.dto.response;

import com.mainul35.bank.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponse {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private BigDecimal newBalance;
    private TransactionType txnType;
    private String txnRef;
    private OffsetDateTime createdAt;
}
