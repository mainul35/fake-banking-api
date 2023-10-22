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

public class WithdrawSteps {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    private CustomerResponse customerResponse;
    private BankAccountResponse bankAccountResponse;
    private String txnRef;

    @Given("the customer's account has been selected")
    public void the_customer_account_has_been_selected() {
        this.customerResponse = customerService.getCustomerById(1L);
        assertNotNull(this.customerResponse);
        assertEquals("a.barron@gmail.com", this.customerResponse.email(), "Fetched customer successfully");
        var accounts = bankAccountService.findBankAccountsOfCustomerByCustomerEmail(this.customerResponse.email());
        this.bankAccountResponse = accounts.get(0);
        assertNotNull(this.bankAccountResponse);
    }

    @Given("the customer with selected account has balance of {string}")
    public void the_customer_with_selected_account_has_balance_of(String initialBalance) {
        assertEquals(new BigDecimal(initialBalance), this.bankAccountResponse.balance(), "Initial balance meets expectation");
    }

    @When("withdraw {string} from the account")
    public void deposit_given_money_in_the_account(String balanceToWithdraw) {
        var balanceToDeduct = new BigDecimal(balanceToWithdraw);
        var accountReq = this.bankAccountResponse.toRequest();
        var expectedBalance = new BigDecimal("379.34");
        this.bankAccountResponse = this.bankAccountService.withdrawMoneyFromAccount(accountReq, balanceToDeduct);
        assertEquals(expectedBalance, this.bankAccountResponse.balance(), "Withdraw successful");
        var txnRequest = new TransactionRequest(bankAccountResponse.toRequest(), balanceToDeduct, this.bankAccountResponse.balance(), TransactionType.WITHDRAW, null);
        txnRef = transactionService.saveTransaction(txnRequest);
        assertNotNull(txnRef, "txnRef is not null");
    }

    @Then("the new balance of the account should be {string}")
    public void the_new_balance_should_be(String balance) {
        var expectedBalance = new BigDecimal(balance);
        var txnResp = transactionService.getTransaction(txnRef);
        assertEquals(expectedBalance, txnResp.newBalance());
        assertEquals(TransactionType.WITHDRAW, txnResp.txnType());
        assertEquals(this.bankAccountResponse, txnResp.account());
    }
}
