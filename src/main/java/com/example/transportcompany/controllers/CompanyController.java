package com.example.transportcompany.controllers;

import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.services.CompanyService;
import com.example.transportcompany.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<List<Company>> getCompanyByName(@RequestParam(required = true) String namePart,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<List<Company>>(companyService.findAllByNameContaining(namePart, page, size), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Company> updateCompany(@Validated @RequestBody Company company) {
        return new ResponseEntity<Company>(companyService.updateCompany(company), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Company> deleteCompany(@RequestBody Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<Company>(HttpStatus.OK);
    }


}
