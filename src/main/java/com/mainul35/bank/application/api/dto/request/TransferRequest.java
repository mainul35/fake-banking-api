package com.mainul35.bank.application.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NotNull
    @NotEmpty
    private String fromAccount;
    @NotNull
    @NotEmpty
    private String toAccount;
    @NotNull
    private BigDecimal amount;
}
