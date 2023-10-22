package com.mainul35.bank.glue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DepositSteps {

    @Given("a customer's account has been selected")
    public void a_customer_account_has_been_selected() {
    }

    @Given("selected account has balance of {string}")
    public void selected_account_has_balance_of(String initialBalance) {
        // Write code here to authenticate the customer
    }

    @When("deposit {string} in the account")
    public void deposit_given_money_in_the_account(String balanceToDeposit) {
    }

    @Then("the newly updated balance of the account should be {string}")
    public void the_newly_updated_balance_of_the_account_should_be(String balance) {
    }
}
