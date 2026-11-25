package com.zbank.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;

    private BigDecimal annualSalary;

    private String employmentType;

    private String documentType;

    @Column(unique = true)
    private String documentNumber;

    private Integer creditScore;

}