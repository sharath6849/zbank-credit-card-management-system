package com.zbank.customer.service;

import com.zbank.customer.dto.CustomerRequest;
import com.zbank.customer.dto.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(
            CustomerRequest request);

    CustomerResponse getCustomer(
            Long customerId);

    CustomerResponse getCustomerByDocumentNumber(
            String documentNumber);
    void updateCreditScore(
            Long customerId,
            Integer creditScore);
}