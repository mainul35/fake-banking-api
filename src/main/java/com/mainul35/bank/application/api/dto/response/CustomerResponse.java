package com.mainul35.bank.application.api.dto.response;

import com.mainul35.bank.application.api.dto.request.CustomerRequest;

public record CustomerResponse (String id, String name, String email){

    public CustomerRequest toRequest() {
        return new CustomerRequest(id, name, email);
    }
}
