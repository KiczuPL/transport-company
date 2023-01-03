package com.example.transportcompany.model.specifications;

import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.requests.OrderRequest;
import com.example.transportcompany.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderSpecification {
    private final CompanyRepository companyRepository;

    public Specification<Order> getSpecification(OrderRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getCompanyName() != null) {
                List<Long> companies = companyRepository.findAllByNameContaining(request.getCompanyName()).stream().map(Company::getId).collect(Collectors.toList());

                predicates.add(criteriaBuilder.or(companies.stream().map((id) -> criteriaBuilder.equal(root.get("companyId"), id)).toArray(Predicate[]::new)));
            }
            if (request.getAddressFrom() != null) {
                predicates.add(criteriaBuilder.like(root.get("addressFrom"), "%" + request.getAddressFrom() + "%"));
            }
            if (request.getAddressTo() != null) {
                predicates.add(criteriaBuilder.like(root.get("addressTo"), "%" + request.getAddressTo() + "%"));
            }
            if (request.getPickUpDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pickUpDate"), request.getPickUpDateFrom()));
            }
            if (request.getPickUpDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pickUpDate"), request.getPickUpDateTo()));
            }
            if (request.getVehicleType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("vehicleType"), request.getVehicleType()));
            }
            if (request.getCreationDateTimeFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDateTime"), request.getCreationDateTimeFrom()));
            }
            if (request.getCreationDateTimeTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDateTime"), request.getCreationDateTimeTo()));
            }
            if (request.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
