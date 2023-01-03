package com.example.transportcompany.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    String username;
    String firstName;
    String lastName;
    String email;
    String companyName;
    Integer pageNumber;
    Integer pageSize;
}
