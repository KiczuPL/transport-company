package com.example.transportcompany.repositories;

import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByNameContaining(String name, Pageable pageable);
    Company findCompanyById(Long id);
    List<Company> findAllByNameContaining(String name);

    Page<Company> findAll(Specification<Company> spec, Pageable pageable);
}
