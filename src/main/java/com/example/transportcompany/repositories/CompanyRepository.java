package com.example.transportcompany.repositories;

import com.example.transportcompany.model.dao.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByNameContaining(String name, Pageable pageable);
}
