package com.mainul35.bank.glue;

import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import com.mainul35.bank.application.api.dto.response.CustomerResponse;
import com.mainul35.bank.application.services.impl.BankAccountService;
import com.mainul35.bank.application.services.impl.CustomerService;
import com.mainul35.bank.application.services.impl.TransactionService;
import com.mainul35.bank.enums.TransactionType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DepositSteps {


    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    private CustomerResponse customerResponse;
    private BankAccountResponse bankAccountResponse;
    private String txnRef;


    @Given("a customer's account has been selected")
    public void a_customer_account_has_been_selected() {
        this.customerResponse = customerService.getCustomerById(1L);
        assertNotNull(this.customerResponse);
        assertEquals("a.barron@gmail.com", this.customerResponse.email(), "Fetched customer successfully");
        var accounts = bankAccountService.findBankAccountsOfCustomerByCustomerEmail(this.customerResponse.email());
        this.bankAccountResponse = accounts.get(0);
        assertNotNull(this.bankAccountResponse);
    }

    @Given("selected account has balance of {string}")
    public void selected_account_has_balance_of(String initialBalance) {
        assertEquals(new BigDecimal(initialBalance), this.bankAccountResponse.balance(), "Initial balance meets expectation");
    }

    @When("deposit {string} in the account")
    public void deposit_given_money_in_the_account(String balanceToDeposit) {
        var balanceToAdd = new BigDecimal(balanceToDeposit);
        var accountReq = this.bankAccountResponse.toRequest();
        var expectedBalance = new BigDecimal("500.22");
        this.bankAccountResponse = this.bankAccountService.addMoneyToAccount(accountReq, balanceToAdd);
        assertEquals(expectedBalance, this.bankAccountResponse.balance(), "Deposited successfully");
        var txnRequest = new TransactionRequest(bankAccountResponse.toRequest(), balanceToAdd, bankAccountResponse.balance(), TransactionType.DEPOSIT, null);
        txnRef = transactionService.saveTransaction(txnRequest);
        assertNotNull(txnRef, "txnRef is not null");
    }

    @Then("the newly updated balance of the account should be {string}")
    public void the_newly_updated_balance_of_the_account_should_be(String balance) {
        var expectedBalance = new BigDecimal(balance);
        var txnResp = transactionService.getTransaction(txnRef);
        assertEquals(expectedBalance, txnResp.newBalance());
        assertEquals(TransactionType.DEPOSIT, txnResp.txnType());
        assertEquals(this.bankAccountResponse, txnResp.account());
    }
}
