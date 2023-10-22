package com.mainul35.bank.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainul35.bank.application.api.dto.request.CustomerRequest;
import com.mainul35.bank.domain.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Profile({"test"})
@Component
public class InitializeTestData implements InitializeData {

    private final CustomerRepository customerRepository;

    private final ResourceLoader resourceLoader;

    public InitializeTestData(CustomerRepository customerRepository, ResourceLoader resourceLoader) {
        this.customerRepository = customerRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void initialize() {
        addCustomers ();
    }

    @Override
    public void doCleanUp() {
//        userRepository.deleteAll ();
    }

    private void addCustomers () {

        InitializeTestData.populateCustomers(customerRepository, resourceLoader);
    }

    static void populateCustomers(CustomerRepository customerRepository, ResourceLoader resourceLoader) {
        if (customerRepository.count() < 1) {
            try {
                List<CustomerRequest> customerModels = new ObjectMapper()
                        .readValue (
                                resourceLoader.getResource ("classpath:customers.json").getInputStream (),
                                new TypeReference<ArrayList<CustomerRequest>>() {
                                }
                        );
                customerModels.forEach (customerRequest -> {
                    customerRepository.save (customerRequest.toEntity());
                });
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }
}
