package com.mainul35.bank.application.api.dto.request;

import com.mainul35.bank.domain.entity.Customer;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(String id, String name, @NotNull String email) {
    public CustomerRequest id(String id) {
        return new CustomerRequest(id, name, email);
    }
    public Customer toEntity() {
        return Customer.builder().id(id).name(name).email(email).build();
    }
}
