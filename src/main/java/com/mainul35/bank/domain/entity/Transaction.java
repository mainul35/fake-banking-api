package com.mainul35.bank.domain.entity;

import com.mainul35.bank.application.api.dto.response.TransactionResponse;
import com.mainul35.bank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private BankAccount account;


    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "new_balance")
    private BigDecimal newBalance;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType txnType;

    @Column(name = "txn_ref")
    private String txnReference;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    public Transaction(Builder builder) {
        setId(builder.id);
        setAccount(builder.account);
        setAmount(builder.amount);
        setNewBalance(builder.newBalance);
        setTxnType(builder.txnType);
        setCreatedAt(builder.createdAt);
        setTxnReference(builder.txnReference);
    }

    public Transaction() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public TransactionResponse toResponse() {
        return new TransactionResponse(this.id, this.account.toResponse(), this.amount, this.newBalance, this.txnType, this.txnReference, this.createdAt);
    }

    public static class Builder {
        private Long id;
        private BankAccount account;
        private BigDecimal amount;
        private BigDecimal newBalance;
        private TransactionType txnType;
        private String txnReference;
        private OffsetDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder account(BankAccount account) {
            this.account = account;
            return this;
        }
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder newBalance(BigDecimal newBalance) {
            this.newBalance = newBalance;
            return this;
        }

        public Builder txnType(TransactionType transactionType) {
            this.txnType = transactionType;
            return this;
        }

        public Builder txnReference(String txnReference) {
            this.txnReference = txnReference;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
