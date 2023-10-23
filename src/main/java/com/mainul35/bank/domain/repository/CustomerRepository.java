package com.mainul35.bank.domain.repository;

import com.mainul35.bank.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);
}
