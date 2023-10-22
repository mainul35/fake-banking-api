package com.mainul35.bank.domain.repository;

import com.mainul35.bank.domain.entity.BankAccount;
import com.mainul35.bank.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    List<BankAccount> findBankAccountByCustomer_Email(String email);
}
