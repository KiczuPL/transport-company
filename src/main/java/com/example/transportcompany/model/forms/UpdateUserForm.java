package com.example.transportcompany.model.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserForm {
    @NotNull(message = "id cannot be null")
    private Long id;
    @NotNull(message = "username cannot be null")
    @NotBlank
    private String username;
    @NotNull(message = "email cannot be null")
    @NotBlank
    private String email;
    @NotNull(message = "first name cannot be null")
    @NotBlank
    private String firstName;
    @NotNull(message = "lase name cannot be null")
    @NotBlank
    private String lastName;
    @NotNull
    private Long companyId;
}
