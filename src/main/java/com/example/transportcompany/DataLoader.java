package com.example.transportcompany;

import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.RoleRepository;
import com.example.transportcompany.repositories.UserRepository;
import com.example.transportcompany.security.RoleEnum;
import com.example.transportcompany.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.example.transportcompany.security.RoleEnum.ROLE_ADMIN;
import static com.example.transportcompany.security.RoleEnum.ROLE_USER;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    @PostConstruct
    public void loadData() {
        roleRepository.saveAll(List.of(
                new Role(null, ROLE_ADMIN.toString()),
                new Role(null, ROLE_USER.toString())

        ));

        Company company  = new Company(null,"asd","asd","asd");
        companyRepository.save(company);

        userService.saveUser(new User(null, "Kiczu@@@asd", "Kiczu", "1234", company, new ArrayList<>()));
        userService.saveUser(new User(null, "user@", "user", "pass", company, new ArrayList<>()));


        userService.addRoleToUser("Kiczu",ROLE_ADMIN.toString());
        userService.addRoleToUser("Kiczu", ROLE_USER.toString());
        userService.addRoleToUser("user",ROLE_USER.toString());

    }
}
