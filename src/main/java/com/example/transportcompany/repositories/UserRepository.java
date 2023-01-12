package com.example.transportcompany.repositories;

import com.example.transportcompany.model.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Page<User> findAllByCompanyId(Long companyId, Pageable pageable);

    void deleteAllByCompanyId(Long companyId);

    void deleteByUsername(String username);

    Page<User> findAllByUsernameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(String username, String firstName, String lastName, String email, String name, Pageable pageable);

    User findByEmail(String email);
}
