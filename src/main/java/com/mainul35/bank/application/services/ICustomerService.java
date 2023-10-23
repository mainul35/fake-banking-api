package com.mainul35.bank.application.services;

import com.mainul35.bank.application.api.dto.request.CustomerRequest;

public interface ICustomerService {
    <T> T getCustomerByEmail(String email);
    <T> T getCustomerById(String customerId);
    <T> T createCustomer(CustomerRequest customerRequest);
}
