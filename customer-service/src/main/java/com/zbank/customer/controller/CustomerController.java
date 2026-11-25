package com.zbank.customer.controller;

import com.zbank.customer.dto.CreditScoreUpdateRequest;
import com.zbank.customer.dto.CustomerRequest;
import com.zbank.customer.dto.CustomerResponse;
import com.zbank.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse>
    createCustomer(
            @Valid
            @RequestBody CustomerRequest request){

        return ResponseEntity.ok(
                customerService.createCustomer(
                        request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse>
    getCustomer(
            @PathVariable Long id){

        return ResponseEntity.ok(
                customerService.getCustomer(id));
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<CustomerResponse>
    getCustomerByDocumentNumber(
            @PathVariable String documentNumber) {

        return ResponseEntity.ok(
                customerService
                        .getCustomerByDocumentNumber(
                                documentNumber));
    }

    @PutMapping("/{id}/credit-score")
    public ResponseEntity<Void> updateCreditScore(

            @PathVariable Long id,

            @RequestBody
            CreditScoreUpdateRequest request) {

        customerService.updateCreditScore(
                id,
                request.creditScore());

        return ResponseEntity.ok().build();
    }
}