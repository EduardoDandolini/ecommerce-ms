package com.ms.custormer.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    String id;
    String firstname;
    String lastname;
    String email;
    Address address;

}
