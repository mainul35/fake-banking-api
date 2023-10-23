package com.mainul35.bank.application.api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountInfoRequest(@NotNull BigDecimal initBalance) {

}
