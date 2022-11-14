package com.example.transportcompany.services;


import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    @RolesAllowed("ROLE_ADMIN")
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @RolesAllowed("ROLE_ADMIN")
    public Company getCompany(Long id) {
        return companyRepository.getReferenceById(id);
    }

    @RolesAllowed("ROLE_ADMIN")
    public List<Company> findAllByNameContaining(String namePart, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return companyRepository.findAllByNameContaining(namePart, pageRequest);
    }

    @RolesAllowed("ROLE_ADMIN")
    public Company updateCompany(Company company) {
        try {
            Company companyById = companyRepository.getReferenceById(company.getId());
            log.info("Updating vehicle: {}  to: {}", companyById.toString(), company.toString());
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
        return companyRepository.save(company);
    }

    @RolesAllowed("ROLE_ADMIN")
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
