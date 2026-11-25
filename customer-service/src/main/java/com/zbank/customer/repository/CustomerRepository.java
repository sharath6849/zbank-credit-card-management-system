package com.zbank.customer.repository;

import com.zbank.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    Optional<Customer> findByDocumentNumber(
            String documentNumber);

    boolean existsByDocumentNumber(
            String documentNumber);
}