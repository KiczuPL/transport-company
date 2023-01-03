package com.example.transportcompany.controllers;

import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.requests.CompanyRequest;
import com.example.transportcompany.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/save")
    public ResponseEntity<Company> saveCompany(@RequestBody Company company) {
        return new ResponseEntity<Company>(companyService.saveCompany(company), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return new ResponseEntity<Company>(companyService.getCompany(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCompanies(@RequestParam(required = false) String name, @RequestParam(required = false) String taxId, @RequestParam(required = false) String address,
                                                            @RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<Map<String, Object>>(companyService.getCompanies(new CompanyRequest(name, taxId, address, page, size)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Company> updateCompany(@Validated @RequestBody Company company) {
        return new ResponseEntity<Company>(companyService.updateCompany(company), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Company> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompanyAndAllAssociatedUsersAndOrders(id);
        return new ResponseEntity<Company>(HttpStatus.OK);
    }

}
