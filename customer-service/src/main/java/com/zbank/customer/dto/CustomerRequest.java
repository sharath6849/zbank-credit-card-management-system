package com.zbank.customer.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CustomerRequest(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @Email(message = "Invalid email")
        String email,

        @Pattern(
                regexp = "\\d{10}",
                message = "Phone number must contain 10 digits")
        String phone,

        @Positive
        BigDecimal annualSalary,

        @NotBlank
        String employmentType,

        @NotBlank
        String documentType,

        @NotBlank
        String documentNumber

) {}
