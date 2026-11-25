package com.zbank.customer.dto;

import java.math.BigDecimal;

public record CustomerResponse(

        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        BigDecimal annualSalary,
        String employmentType,
        String documentType,
        String documentNumber,
        Integer creditScore


) {}