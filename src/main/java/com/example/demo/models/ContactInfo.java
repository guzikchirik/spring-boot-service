package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInfo {
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
}
