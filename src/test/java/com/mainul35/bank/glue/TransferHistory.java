package com.mainul35.bank.glue;

import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import com.mainul35.bank.application.api.dto.response.CustomerResponse;
import com.mainul35.bank.application.api.dto.response.TransactionHistoryResponse;
import com.mainul35.bank.application.services.impl.BankAccountService;
import com.mainul35.bank.application.services.impl.CustomerService;
import com.mainul35.bank.application.services.impl.TransactionService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransferHistory {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    private BankAccountResponse customerAccountForHistory;

    private List<TransactionHistoryResponse> transactionHistoryResponses;

    private static final Logger LOGGER = LoggerFactory.getLogger("com.mainul35.bank.glue.TransferHistory");
    @Given("A customer's bank account has been selected")
    public void a_customer_s_bank_account_has_been_selected () {
        CustomerResponse selectedCustomer = customerService.getCustomerById(1L);
        assertNotNull(selectedCustomer);
        customerAccountForHistory = bankAccountService.findBankAccountsOfCustomerByCustomerEmail(selectedCustomer.email()).get(0);
        assertNotNull(customerAccountForHistory);
    }

    @When("the account's transfer history is requested")
    public void the_account_s_transfer_history_is_requested () {
        this.transactionHistoryResponses = transactionService.getTransactionByAccountNumber(customerAccountForHistory.accountNumber());
    }

    @Then("transfer history is fetched")
    public void transfer_history_is_fetched () {
        transactionHistoryResponses.forEach(transactionHistoryResponse -> {
            LOGGER.debug(transactionHistoryResponse.toString());
            System.out.println(transactionHistoryResponse.toString());
        });
    }
}
