package com.mainul35.bank.glue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SameCustomerDifferentAccountsMoneyTransferSteps {
    private double customerBalance;
    private double recipientBalance;

    @Given("the senderAccount has been selected")
    public void the_sender_account_has_been_selected() {
        // Write code here to authenticate the customer
    }
    @Given("the recipientAccount has been selected")
    public void the_recipientAccount_has_been_selected() {
        // Write code here to authenticate the customer
    }

    @Given("the customer with senderAccount has balance of {string}")
    public void the_customer_with_senderAccount_has_balance_of(String balance) {
        this.customerBalance = Double.parseDouble(balance);
    }

    @Given("the customer with recipientAccount has balance of {string}")
    public void the_customer_with_recipientAccount_has_balance_of(String balance) {
        this.recipientBalance = Double.parseDouble(balance);
    }

    @When("transfer {string} to recipientAccount from senderAccount")
    public void the_customer_transfers_to_the_recipient(String amount) {
        double transferAmount = Double.parseDouble(amount);
        this.customerBalance -= transferAmount;
        this.recipientBalance += transferAmount;
    }

    @Then("the new balance of the senderAccount should be {string}")
    public void the_new_balance_of_the_senderAccount_should_be(String expectedBalance) {
//        assertEquals(Double.parseDouble(expectedBalance), this.customerBalance, 0.01);
    }

    @Then("the new balance of the recipientAccount should be {string}")
    public void the_new_balance_of_the_recipientAccount_should_be(String expectedBalance) {
//        assertEquals(Double.parseDouble(expectedBalance), this.recipientBalance, 0.01);
    }

}
