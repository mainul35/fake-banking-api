package com.mainul35.bank.glue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DifferentCustomerAccountsMoneyTransferSteps {
    private double customerBalance;
    private double recipientBalance;

    @Given("An account has been selected to be fromAccount")
    public void an_account_has_been_selected_to_be_fromAccount() {
        // Write code here to authenticate the customer
    }
    @Given("Another account has been selected to be toAccount")
    public void another_account_has_been_selected_to_be_toAccount() {
        // Write code here to authenticate the customer
    }

    @Given("fromAccount has balance of {string}")
    public void fromAccount_has_balance_of(String balance) {
        this.customerBalance = Double.parseDouble(balance);
    }

    @Given("toAccount has balance of {string}")
    public void toAccount_has_balance_of(String balance) {
        this.recipientBalance = Double.parseDouble(balance);
    }

    @When("transfer {string} to toAccount from fromAccount")
    public void transfer_money_to_toAccount_from_fromAccount(String amount) {
        double transferAmount = Double.parseDouble(amount);
        this.customerBalance -= transferAmount;
        this.recipientBalance += transferAmount;
    }

    @Then("the new balance of the fromAccount should be {string}")
    public void the_new_balance_of_the_fromAccount_should_be(String expectedBalance) {
//        assertEquals(Double.parseDouble(expectedBalance), this.customerBalance, 0.01);
    }

    @Then("the new balance of the toAccount should be {string}")
    public void the_new_balance_of_the_toAccount_should_be(String expectedBalance) {
//        assertEquals(Double.parseDouble(expectedBalance), this.recipientBalance, 0.01);
    }

}
