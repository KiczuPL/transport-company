package com.example.transportcompany.model.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDTO {

    private Long userId;
    @NotNull(message = "email cannot be null")
    @NotBlank
    private String email;
    @NotNull(message = "login cannot be null")
    @NotBlank
    private String login;
    @NotNull(message = "password cannot be null")
    @NotBlank
    private String password;
    @NotNull(message = "companyId cannot be null")
    private Long companyId;
    @NotNull(message = "roleId cannot be null")
    private Long roleId;
}
