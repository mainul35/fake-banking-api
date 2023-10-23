package com.mainul35.bank.application.api;

import com.mainul35.bank.application.api.dto.request.*;
import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import com.mainul35.bank.application.api.dto.response.TransactionReferenceResponse;
import com.mainul35.bank.application.services.IBankAccountService;
import com.mainul35.bank.application.services.ICustomerService;
import com.mainul35.bank.application.services.ITransactionService;
import com.mainul35.bank.enums.TransactionType;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountController {

    private final IBankAccountService bankAccountService;
    private final ITransactionService transactionService;
    private final ICustomerService customerService;

    public AccountController(IBankAccountService bankAccountService, ITransactionService transactionService, ICustomerService customerService) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
        this.customerService = customerService;
    }
    @PostMapping
    public ResponseEntity<BankAccountResponse> createAccount (@RequestBody @Valid BankAccountRequest bankAccountRequest) {
        String accNo = this.bankAccountService.createCustomerAndAccount(bankAccountRequest);
        BankAccountResponse resp = bankAccountService.findAccountByAccountNumber(accNo);
        TransactionRequest txReq = new TransactionRequest(resp.toRequest(), bankAccountRequest.balance(), resp.balance(), TransactionType.DEPOSIT, UUID.randomUUID().toString());
        transactionService.saveTransaction(txReq);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getByAccountNumber (@PathVariable("accountNumber") String accountNumber) {
        return ResponseEntity.ok(bankAccountService.findAccountByAccountNumber(accountNumber));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getListByEmail (@RequestParam("email") String email) {
        return ResponseEntity.ok(bankAccountService.findBankAccountsOfCustomerByCustomerEmail(email));
    }

    @PutMapping("/deposit")
    public ResponseEntity<?> deposit (@RequestBody @Valid DepositRequest depositRequest) {
        BankAccountResponse accountResponse = this.bankAccountService.findAccountByAccountNumber(depositRequest.accountNumber());
        accountResponse = this.bankAccountService.addMoneyToAccount(accountResponse.toRequest(), depositRequest.amount());
        TransactionRequest transaction = new TransactionRequest(accountResponse.toRequest(), depositRequest.amount(), accountResponse.balance(), TransactionType.DEPOSIT, null);
        var txRef = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(new TransactionReferenceResponse(txRef));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw (@RequestBody WithdrawRequest withdrawRequest) {
        BankAccountResponse accountRequestFromDb = this.bankAccountService.findAccountByAccountNumber(withdrawRequest.accountNumber());
        accountRequestFromDb = this.bankAccountService.withdrawMoneyFromAccount(accountRequestFromDb.toRequest(), withdrawRequest.amount());
        TransactionRequest transaction = new TransactionRequest(accountRequestFromDb.toRequest(), withdrawRequest.amount(), accountRequestFromDb.balance(), TransactionType.DEPOSIT, null);
        var txRef = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(new TransactionReferenceResponse(txRef));
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> transfer (@RequestBody @Valid TransferRequest transferRequest) {
        BankAccountResponse senderAccFromDb = this.bankAccountService.findAccountByAccountNumber(transferRequest.getFromAccount());
        BankAccountResponse receiverAccFromDb = this.bankAccountService.findAccountByAccountNumber(transferRequest.getToAccount());
        senderAccFromDb = bankAccountService.withdrawMoneyFromAccount(senderAccFromDb.toRequest(), transferRequest.getAmount());
        receiverAccFromDb = bankAccountService.addMoneyToAccount(receiverAccFromDb.toRequest(), transferRequest.getAmount());

        TransactionRequest transaction = new TransactionRequest(senderAccFromDb.toRequest(), transferRequest.getAmount(), senderAccFromDb.balance(), TransactionType.TRANSFER, null);
        var txRef = transactionService.saveTransaction(transaction);
        TransactionRequest transaction2 = new TransactionRequest(receiverAccFromDb.toRequest(), transferRequest.getAmount(), receiverAccFromDb.balance(), TransactionType.DEPOSIT, txRef);
        txRef = transactionService.saveTransaction(transaction2);
        return ResponseEntity.ok(new TransactionReferenceResponse(txRef));
    }
}
