package com.abs.banking.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String addressLine1;

    private String addressLine2;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String zipCode;

    @NotNull
    private Date created;


}
