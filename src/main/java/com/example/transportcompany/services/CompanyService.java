package com.example.transportcompany.services;


import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company saveCompany(Company company){
        return companyRepository.save(company);
    }
}
