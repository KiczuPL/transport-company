package com.example.transportcompany.model.specifications;

import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.requests.CompanyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanySpecification {
    public Specification<Company> getSpecification(CompanyRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
            }
            if (request.getTaxId() != null) {
                predicates.add(criteriaBuilder.like(root.get("taxIdNumber"), "%" + request.getTaxId() + "%"));
            }
            if (request.getAddress() != null) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + request.getAddress() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
