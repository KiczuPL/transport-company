package com.example.transportcompany.services;


import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company getCompany(Long id) {
        return companyRepository.getReferenceById(id);
    }

    public Company updateCompany(Company company) {
        try {
            Company companyById = companyRepository.getReferenceById(company.getId());
            log.info("Updating vehicle: {}  to: {}", companyById.toString(), company.toString());
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id){
        companyRepository.deleteById(id);
    }
}
