package com.example.transportcompany.security;

public enum RoleEnum {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private final String role_user;

    RoleEnum(String role_user) {
        this.role_user = role_user;
    }

    public String toString() {
        return role_user;
    }
}
