package com.mainul35.bank.application.services;

public interface ICustomerService {
    <T> T getCustomerByEmail(String email);
    <T> T getCustomerById(Long customerId);
}
