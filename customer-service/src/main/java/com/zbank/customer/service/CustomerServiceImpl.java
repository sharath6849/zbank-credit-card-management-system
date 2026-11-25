package com.zbank.customer.service;

import com.zbank.customer.dto.CustomerRequest;
import com.zbank.customer.dto.CustomerResponse;
import com.zbank.customer.entity.Customer;
import com.zbank.customer.exception.CustomerAlreadyExistsException;
import com.zbank.customer.exception.CustomerNotFoundException;
import com.zbank.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public CustomerResponse createCustomer(
            CustomerRequest request) {

        if(repository.existsByDocumentNumber(
                request.documentNumber())) {

            throw new CustomerAlreadyExistsException(
                    "Customer already exists");
        }

        Customer customer =
                Customer.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .email(request.email())
                        .phone(request.phone())
                        .annualSalary(request.annualSalary())
                        .employmentType(request.employmentType())
                        .documentType(request.documentType())
                        .documentNumber(request.documentNumber())
                        .build();

        Customer saved =
                repository.save(customer);

        return map(saved);
    }

    @Override
    public CustomerResponse getCustomer(
            Long customerId) {

        Customer customer =
                repository.findById(customerId)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found with id : " + customerId
                                ));

        return map(customer);
    }

    @Override
    public CustomerResponse getCustomerByDocumentNumber(
            String documentNumber) {

        Customer customer =
                repository.findByDocumentNumber(documentNumber)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found"));

        return map(customer);
    }

    @Override
    public void updateCreditScore(
            Long customerId,
            Integer creditScore) {

        Customer customer = repository
                .findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found"));

        customer.setCreditScore(creditScore);

        repository.save(customer);
    }

    private CustomerResponse map(
            Customer customer) {

        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAnnualSalary(),
                customer.getEmploymentType(),
                customer.getDocumentType(),
                customer.getDocumentNumber(),
                customer.getCreditScore()
        );
    }
}