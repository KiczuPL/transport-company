package com.example.transportcompany.services;


import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.requests.CompanyRequest;
import com.example.transportcompany.model.specifications.CompanySpecification;
import com.example.transportcompany.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final OrderService orderService;
    private final UserService userService;
    private final CompanySpecification companySpecification;

    @RolesAllowed({"ROLE_ADMIN"})
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public Company getCompany(Long id) {
        return companyRepository.getReferenceById(id);
    }


    public List<Company> findAllByNameContaining(String namePart, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return companyRepository.findAllByNameContaining(namePart, pageRequest);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @Transactional
    public Map<String, Object> getCompanies(CompanyRequest request) {
        int pageNumber = request.getPageNumber() == null ? 0 : request.getPageNumber();
        int pageSize = request.getPageSize() == null ? 10 : request.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        log.info("Handling get companies request {}", request.toString());
        Page<Order> pageOrder = companyRepository.findAll(companySpecification.getSpecification(request), pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("companies", pageOrder.getContent());
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());
        return response;
    }

    @RolesAllowed("ROLE_ADMIN")
    public Company updateCompany(Company company) {
        Optional<Company> companyById = companyRepository.findById(company.getId());
        if (companyById.isEmpty())
            throw new IllegalArgumentException("No such company is present in database.");

        log.info("Updating company: {}  to: {}", companyById.get().toString(), company.toString());
        return companyRepository.save(company);
    }

    @RolesAllowed("ROLE_ADMIN")
    @Transactional
    public void deleteCompanyAndAllAssociatedUsersAndOrders(Long id) {
        Company company = companyRepository.getReferenceById(id);
        log.info("Deleting company {}", company.toString());
        orderService.deleteAllOrdersFromCompany(id);
        userService.deleteAllUsersFromCompany(id);
        companyRepository.deleteById(id);
    }
}
