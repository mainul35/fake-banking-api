package com.mainul35.bank.application.api.dto.request;

import com.mainul35.bank.domain.entity.Customer;

public record CustomerRequest(String id, String name, String email) {
    public Customer toEntity() {
        return Customer.builder().id(id).name(name).email(email).build();
    }
}
