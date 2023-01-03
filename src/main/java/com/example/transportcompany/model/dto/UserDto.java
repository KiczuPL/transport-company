package com.example.transportcompany.model.dto;

import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dao.User;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class UserDto {
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
    @ManyToOne
    private Company company;
    private Collection<Role> roles;

    public UserDto(User user) {
        this.id = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.company = user.getCompany();
        this.roles = user.getRoles();
    }
}
