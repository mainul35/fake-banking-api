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

    @GetMapping("/search")
    public ResponseEntity<CustomerResponse> getByEmail (@RequestParam("email") String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById (@PathVariable("id") String id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
}
