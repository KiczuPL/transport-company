package com.example.transportcompany.model.dao;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @NotNull(message = "username cannot be null")
    @NotBlank
    private String username;
    @NotNull(message = "password cannot be null")
    @NotBlank
    private String password;
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
    @NotNull(message = "roleId cannot be null")
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
