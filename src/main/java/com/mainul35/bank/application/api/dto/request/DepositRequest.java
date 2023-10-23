package com.mainul35.bank.application.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositRequest(
        @NotNull
        @NotEmpty
        String accountNumber,
        @NotNull
        BigDecimal amount
) {
}
