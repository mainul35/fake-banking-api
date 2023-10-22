package com.mainul35.bank.application.api;

import com.mainul35.bank.application.api.dto.request.CustomerRequest;
import com.mainul35.bank.application.api.dto.response.CustomerResponse;
import com.mainul35.bank.application.services.ICustomerService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final ICustomerService customerService;
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer (@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.createCustomer (customerRequest));
    }

    @GetMapping("/{email}")
    public ResponseEntity<CustomerResponse> getByEmail (@PathVariable("email") String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById (@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
}
