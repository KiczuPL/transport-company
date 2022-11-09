package com.example.transportcompany.model.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity

@Getter
@Setter
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    @NotNull
    private Long companyId;

    public Order() {

    }
}
