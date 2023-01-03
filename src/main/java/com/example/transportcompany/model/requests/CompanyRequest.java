package com.example.transportcompany.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyRequest {
    String name;
    String taxId;
    String address;

    Integer pageNumber;
    Integer pageSize;
}
