package com.mainul35.bank.application.services.impl;

import com.mainul35.bank.application.api.dto.request.CustomerRequest;
import com.mainul35.bank.application.api.dto.response.CustomerResponse;
import com.mainul35.bank.application.services.ICustomerService;
import com.mainul35.bank.domain.repository.CustomerRepository;
import com.mainul35.bank.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        var customer = this.customerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return customer.toResponse();
    }

    @Override
    public CustomerResponse getCustomerById(String customerId) {
        var customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return customer.toResponse();
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        var customer = this.customerRepository.save(customerRequest.toEntity());
        return customer.toResponse();
    }
}
