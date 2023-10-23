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
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SameCustomerDifferentAccountsMoneyTransferSteps {
    private BankAccountResponse senderAccount;
    private BankAccountResponse receiverAccount;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IBankAccountService bankAccountService;

    @Autowired
    private ITransactionService transactionService;

    @Given("the senderAccount has been selected")
    public void the_sender_account_has_been_selected() {
        CustomerResponse selectedCustomer = customerService.getCustomerById("2");
        assertNotNull(selectedCustomer);
        var bankAccReq = new BankAccountRequest(selectedCustomer.toRequest(), new BigDecimal("300.00"), null);
        String accNumber = bankAccountService.createBankAccountForExistingCustomer(bankAccReq);
        senderAccount = bankAccountService.findBankAccountsOfCustomerByCustomerEmail(selectedCustomer.email()).get(0);
        assertNotNull(senderAccount);
    }
    @Given("the recipientAccount has been selected")
    public void the_recipientAccount_has_been_selected() {
        CustomerResponse selectedCustomer = customerService.getCustomerById("2");
        assertNotNull(selectedCustomer);
        var accounts = bankAccountService.findBankAccountsOfCustomerByCustomerEmail(selectedCustomer.email());
        if (accounts.size() < 2) {
            var toAccountReq = new BankAccountRequest(selectedCustomer.toRequest(), new BigDecimal("300.00"), null);
            String toAccountNumber = bankAccountService.createBankAccountForExistingCustomer(toAccountReq);
            receiverAccount = bankAccountService.findAccountByAccountNumber(toAccountNumber);
        }
        assertNotNull(receiverAccount);
    }

    @Given("the customer with senderAccount has balance of {string}")
    public void the_customer_with_senderAccount_has_balance_of(String balance) {
        var initAmount = new BigDecimal(balance);
        assertEquals(initAmount, senderAccount.balance());
    }

    @Given("the customer with recipientAccount has balance of {string}")
    public void the_customer_with_recipientAccount_has_balance_of(String balance) {
        var initAmount = new BigDecimal(balance);
        assertEquals(initAmount, receiverAccount.balance());
    }

    @When("transfer {string} to recipientAccount from senderAccount")
    public void the_customer_transfers_to_the_recipient(String amount) {
        var balanceToDeduct = new BigDecimal(amount);
        BankAccountResponse withdrawAccountResp = bankAccountService.withdrawMoneyFromAccount(senderAccount.toRequest(), balanceToDeduct);
        BankAccountResponse depositAccountResp = bankAccountService.addMoneyToAccount(receiverAccount.toRequest(), balanceToDeduct);

        assertEquals(senderAccount.balance().subtract(balanceToDeduct), withdrawAccountResp.balance());
        assertEquals(receiverAccount.balance().add(balanceToDeduct), depositAccountResp.balance());

        senderAccount = withdrawAccountResp;
        receiverAccount = depositAccountResp;

        var tx = new TransactionRequest(senderAccount.toRequest(), balanceToDeduct, senderAccount.balance(), TransactionType.TRANSFER, null);
        String txRef = transactionService.saveTransaction(tx);
        var tx2 = new TransactionRequest(receiverAccount.toRequest(), balanceToDeduct, receiverAccount.balance(), TransactionType.DEPOSIT, txRef);
        String txRef2 = transactionService.saveTransaction(tx2);
        assertEquals(txRef, txRef2);
    }

    @Then("the new balance of the senderAccount should be {string}")
    public void the_new_balance_of_the_senderAccount_should_be(String expectedBalance) {
        var expected = new BigDecimal(expectedBalance);
        assertEquals(expected, senderAccount.balance());
    }

    @Then("the new balance of the recipientAccount should be {string}")
    public void the_new_balance_of_the_recipientAccount_should_be(String expectedBalance) {
        var expected = new BigDecimal(expectedBalance);
        assertEquals(expected, receiverAccount.balance());
    }
}
