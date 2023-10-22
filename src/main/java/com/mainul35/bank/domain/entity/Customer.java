package com.mainul35.bank.domain.entity;

import com.mainul35.bank.application.api.dto.response.CustomerResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "customer")
public class Customer
{
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    public Customer(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setEmail(builder.email);
    }

    public Customer() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public CustomerResponse toResponse() {
        return new CustomerResponse(this.id, this.name, this.email);
    }

    public static class Builder {
        private String id;

        private String name;

        private String email;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
