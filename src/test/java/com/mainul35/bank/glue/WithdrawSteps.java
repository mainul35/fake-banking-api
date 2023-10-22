package com.mainul35.bank.glue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class WithdrawSteps {

    @Given("the customer's account has been selected")
    public void the_customer_account_has_been_selected() {
    }

    @Given("the customer with selected account has balance of {string}")
    public void the_customer_with_selected_account_has_balance_of(String initialBalance) {
        // Write code here to authenticate the customer
    }

    @When("withdraw {string} from the account")
    public void deposit_given_money_in_the_account(String balanceToDeposit) {
    }

    @Then("the new balance of the account should be {string}")
    public void the_new_balance_should_be(String balance) {
    }


}
