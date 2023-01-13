package com.example.transportcompany.model.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordForm {
    @NotNull
    @NotBlank
    private String newPassword;
}
