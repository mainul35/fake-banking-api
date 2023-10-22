package com.mainul35.bank.domain.entity;

import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity(name = "account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "account_number")
    private String accountNumber;

    public void addMoney(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount to add must be positive.");
        }
        this.balance = this.balance.add(amount);
    }

    public void deductMoney(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount to deduct must be positive.");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        this.balance = this.balance.subtract(amount);
    }

    protected BankAccount() {

    }

    public BankAccount(Builder builder) {
        this.customer = builder.customer;
        if (builder.balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance must be positive.");
        }
        this.balance = builder.balance;
        this.accountNumber = builder.accountNumber;
    }


    public static Builder builder() {
        return new Builder();
    }

    public BankAccountResponse toResponse() {
        return new BankAccountResponse(this.id, this.customer.toResponse(), this.balance, this.accountNumber);
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public static class Builder {

        private Customer customer;

        private BigDecimal balance;

        private String accountNumber;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public BankAccount build() {
            return new BankAccount(this);
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }
    }
}
