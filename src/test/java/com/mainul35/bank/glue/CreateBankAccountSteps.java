package com.mainul35.bank.glue;

import com.mainul35.bank.application.api.dto.request.BankAccountRequest;
import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import com.mainul35.bank.application.api.dto.response.CustomerResponse;
import com.mainul35.bank.application.services.IBankAccountService;
import com.mainul35.bank.application.services.ICustomerService;
import com.mainul35.bank.application.services.ITransactionService;
import com.mainul35.bank.enums.TransactionType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateBankAccountSteps {

    private CustomerResponse selectedCustomer;

    private BankAccountResponse newAccount;

    private String createdAccountNumber = null;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IBankAccountService bankAccountService;

    @Autowired
    private ITransactionService transactionService;

    @Given("^A customer profile has been selected with id$")
    public void a_customer_profile_has_been_selected_with_id() {
        this.selectedCustomer = customerService.getCustomerById("1");
        assertNotNull(selectedCustomer);
    }

    @When("the customer requests to create a new bank account with an initial deposit of {string}")
    public void the_customer_requests_to_create_a_new_bank_account_with_an_initial_deposit_of(String initialDeposit) {
        var initAmount = new BigDecimal(initialDeposit);
        var accountRequest = new BankAccountRequest(selectedCustomer.toRequest(), initAmount, null);
        createdAccountNumber = bankAccountService.createBankAccountForExistingCustomer(accountRequest);
        BankAccountResponse bankAccountResponse = bankAccountService.findAccountByAccountNumber(createdAccountNumber);
        var tx = new TransactionRequest(bankAccountResponse.toRequest(), initAmount, bankAccountResponse.balance(), TransactionType.DEPOSIT, null);
        var txRef = transactionService.saveTransaction(tx);
        assertNotNull(createdAccountNumber);
        assertNotNull(txRef);
    }

    @Then("a new bank account should be created")
    public void a_new_bank_account_should_be_created() {
        this.newAccount = bankAccountService.findAccountByAccountNumber(createdAccountNumber);
        assertNotNull(this.newAccount);
        assertNotNull(newAccount.id());
        Assertions.assertEquals(this.createdAccountNumber, newAccount.accountNumber());
    }

    @Then("the balance of the new account should be {string}")
    public void the_balance_of_the_new_account_should_be(String expectedBalance) {
        Assertions.assertEquals(new BigDecimal(expectedBalance), newAccount.balance(), "The initial account balance is correct.");
//        throw new RuntimeException("Unimplemented yet");
    }
}
