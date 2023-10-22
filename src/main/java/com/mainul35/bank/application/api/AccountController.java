package com.mainul35.bank.application.api;

import com.mainul35.bank.application.api.dto.request.BankAccountRequest;
import com.mainul35.bank.application.api.dto.request.TransactionRequest;
import com.mainul35.bank.application.api.dto.request.TransferRequest;
import com.mainul35.bank.application.api.dto.response.BankAccountResponse;
import com.mainul35.bank.application.api.dto.response.TransactionReferenceResponse;
import com.mainul35.bank.application.services.IBankAccountService;
import com.mainul35.bank.application.services.ITransactionService;
import com.mainul35.bank.enums.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final IBankAccountService bankAccountService;

    private final ITransactionService transactionService;

    public AccountController(IBankAccountService bankAccountService, ITransactionService transactionService) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
    }
    @PostMapping
    public ResponseEntity<String> createAccount (@RequestBody BankAccountRequest bankAccountRequest) {
        return ResponseEntity.ok(bankAccountService.createBankAccount(bankAccountRequest));
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
    public ResponseEntity<?> deposit (@RequestBody BankAccountRequest bankAccountRequest) {
        BankAccountRequest accountRequestFromDb = this.bankAccountService.findAccountByAccountNumber(bankAccountRequest.accountNumber());
        BankAccountResponse accResp = this.bankAccountService.addMoneyToAccount(accountRequestFromDb, bankAccountRequest.balance());
        TransactionRequest transaction = new TransactionRequest(accountRequestFromDb, bankAccountRequest.balance(), accResp.balance(), TransactionType.DEPOSIT, null);
        var txRef = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(new TransactionReferenceResponse(txRef));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw (@RequestBody BankAccountRequest bankAccountRequest) {
        BankAccountRequest accountRequestFromDb = this.bankAccountService.findAccountByAccountNumber(bankAccountRequest.accountNumber());
        BankAccountResponse accResp = this.bankAccountService.withdrawMoneyFromAccount(accountRequestFromDb, bankAccountRequest.balance());
        TransactionRequest transaction = new TransactionRequest(accountRequestFromDb, bankAccountRequest.balance(), accResp.balance(), TransactionType.DEPOSIT, null);
        var txRef = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(new TransactionReferenceResponse(txRef));
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> transfer (@RequestBody TransferRequest transferRequest) {
        BankAccountRequest senderAccFromDb = this.bankAccountService.findAccountByAccountNumber(transferRequest.getFromAccount());
        BankAccountRequest receiverAccFromDb = this.bankAccountService.findAccountByAccountNumber(transferRequest.getToAccount());
        senderAccFromDb = bankAccountService.addMoneyToAccount(senderAccFromDb, transferRequest.getAmount());
        receiverAccFromDb = bankAccountService.withdrawMoneyFromAccount(receiverAccFromDb, transferRequest.getAmount());

        TransactionRequest transaction = new TransactionRequest(senderAccFromDb, transferRequest.getAmount(), senderAccFromDb.balance(), TransactionType.TRANSFER, null);
        var txRef = transactionService.saveTransaction(transaction);
        TransactionRequest transaction2 = new TransactionRequest(senderAccFromDb, transferRequest.getAmount(), receiverAccFromDb.balance(), TransactionType.DEPOSIT, txRef);
        txRef = transactionService.saveTransaction(transaction2);
        return ResponseEntity.ok(new TransactionReferenceResponse(txRef));
    }
}
