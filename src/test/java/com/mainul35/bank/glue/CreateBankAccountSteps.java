package com.mainul35.bank.glue;

import com.mainul35.bank.FakeBankingApiApplication;
import com.mainul35.bank.application.api.dto.response.CustomerResponse;
import com.mainul35.bank.application.services.ICustomerService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {FakeBankingApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class CreateBankAccountSteps {

    private CustomerResponse selectedCustomer;

    @Autowired
    private ICustomerService customerService;

    @Given("^A customer profile has been selected with id$")
    public void a_customer_profile_has_been_selected_with_id() {
        // This is a mock, replace with actual retrieval logic or context setting
        this.selectedCustomer = customerService.getCustomerById(1L);
        Assertions.assertNotNull(selectedCustomer);
    }

    @When("the customer requests to create a new bank account with an initial deposit of {string}")
    public void the_customer_requests_to_create_a_new_bank_account_with_an_initial_deposit_of(String initialDeposit) {
        // This should call the actual service method to create a new account, but here we'll simulate it with a mock for simplicity
//        newAccount = bankAccountService.createBankAccount(selectedCustomer, new BigDecimal(initialDeposit));
        throw new RuntimeException("Could not create a new account");
    }

    @Then("a new bank account should be created")
    public void a_new_bank_account_should_be_created() {
//        verify(bankAccountService, times(1)).createBankAccount(eq(selectedCustomer), any(BigDecimal.class));
    }

    @Then("the balance of the new account should be {string}")
    public void the_balance_of_the_new_account_should_be(String expectedBalance) {
//        assertEquals(new BigDecimal(expectedBalance), newAccount.getBalance());
    }
}
