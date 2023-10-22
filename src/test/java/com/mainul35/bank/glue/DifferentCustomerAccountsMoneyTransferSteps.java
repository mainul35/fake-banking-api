package com.mainul35.bank.glue;

import com.mainul35.bank.application.api.dto.request.BankAccountRequest;
import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import com.mainul35.bank.application.api.dto.response.CustomerResponse;
import com.mainul35.bank.application.services.IBankAccountService;
import com.mainul35.bank.application.services.ICustomerService;
import com.mainul35.bank.application.services.ITransactionService;
import com.mainul35.bank.domain.entity.Customer;
import com.mainul35.bank.enums.TransactionType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DifferentCustomerAccountsMoneyTransferSteps {
    private BankAccountResponse senderAccount;
    private BankAccountResponse receiverAccount;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IBankAccountService bankAccountService;

    @Autowired
    private ITransactionService transactionService;

    @Given("An account has been selected to be fromAccount")
    public void an_account_has_been_selected_to_be_fromAccount() {
        CustomerResponse selectedCustomer = customerService.getCustomerById(1L);
        assertNotNull(selectedCustomer);
        senderAccount = bankAccountService.findBankAccountsOfCustomerByCustomerEmail(selectedCustomer.email()).get(0);
        assertNotNull(senderAccount);
    }
    @Given("Another account has been selected to be toAccount")
    public void another_account_has_been_selected_to_be_toAccount() {
        CustomerResponse selectedCustomer = customerService.getCustomerById(2L);
        assertNotNull(selectedCustomer);
        var initAmount = new BigDecimal("300.00");
        var accReq = new BankAccountRequest(selectedCustomer.toRequest(), initAmount, null);
        String accountNumber = bankAccountService.createBankAccount(accReq);
        receiverAccount = bankAccountService.findAccountByAccountNumber(accountNumber);
        assertNotNull(receiverAccount);
        assertEquals(initAmount, receiverAccount.balance());
        var tx = new TransactionRequest(receiverAccount.toRequest(), initAmount, TransactionType.DEPOSIT, null);
        var txRef = transactionService.saveTransaction(tx);
        assertNotNull(txRef);
    }

    @Given("fromAccount has balance of {string}")
    public void fromAccount_has_balance_of(String balance) {
        senderAccount = bankAccountService.findAccountByAccountNumber(senderAccount.accountNumber());
        var expectedBalance = new BigDecimal(balance);
        assertEquals(expectedBalance, senderAccount.balance());
    }

    @Given("toAccount has balance of {string}")
    public void toAccount_has_balance_of(String balance) {
        receiverAccount = bankAccountService.findAccountByAccountNumber(receiverAccount.accountNumber());
        var expectedBalance = new BigDecimal(balance);
        assertEquals(expectedBalance, receiverAccount.balance());
    }

    @When("transfer {string} to toAccount from fromAccount")
    public void transfer_money_to_toAccount_from_fromAccount(String amount) {
        var balanceToDeduct = new BigDecimal(amount);
        BankAccountResponse withdrawAccountResp = bankAccountService.withdrawMoneyFromAccount(senderAccount.toRequest(), balanceToDeduct);
        BankAccountResponse depositAccountResp = bankAccountService.addMoneyToAccount(receiverAccount.toRequest(), balanceToDeduct);

        assertEquals(senderAccount.balance().subtract(balanceToDeduct), withdrawAccountResp.balance());
        assertEquals(receiverAccount.balance().add(balanceToDeduct), depositAccountResp.balance());

        senderAccount = withdrawAccountResp;
        receiverAccount = depositAccountResp;

        var tx = new TransactionRequest(senderAccount.toRequest(), senderAccount.balance(), TransactionType.WITHDRAW, null);
        String txRef = transactionService.saveTransaction(tx);
        var tx2 = new TransactionRequest(receiverAccount.toRequest(), receiverAccount.balance(), TransactionType.DEPOSIT, txRef);
        String txRef2 = transactionService.saveTransaction(tx2);
        assertEquals(txRef, txRef2);

    }

    @Then("the new balance of the fromAccount should be {string}")
    public void the_new_balance_of_the_fromAccount_should_be(String expectedBalance) {
        var expected = new BigDecimal(expectedBalance);
        assertEquals(expected, senderAccount.balance());
    }

    @Then("the new balance of the toAccount should be {string}")
    public void the_new_balance_of_the_toAccount_should_be(String expectedBalance) {
        var expected = new BigDecimal(expectedBalance);
        assertEquals(expected, receiverAccount.balance());
    }
}
