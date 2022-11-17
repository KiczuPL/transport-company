package com.example.transportcompany.services;

import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.model.dto.UserDto;
import com.example.transportcompany.model.forms.CreateUserForm;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.RoleRepository;
import com.example.transportcompany.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @RolesAllowed("ROLE_ADMIN")
    public UserDto saveUser(CreateUserForm form) {
        User userByUsername = userRepository.findByUsername(form.getUsername());
        if (userByUsername != null)
            throw new IllegalArgumentException("There already exists user with that username.");


        PasswordGenerator passwordGenerator = new PasswordGenerator();
        CharacterRule rule = new CharacterRule(EnglishCharacterData.Alphabetical);
        User user = new User(
                null,
                form.getUsername(),
                passwordGenerator.generatePassword(10, rule),
                form.getEmail(),
                form.getFirstName(),
                form.getLastName(),
                companyRepository.getReferenceById(form.getCompanyId()),
                new ArrayList<>()
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Saving user: " + form.toString());
        return new UserDto(userRepository.save(user));
    }

    @RolesAllowed("ROLE_ADMIN")
    public void changePassword(String username, String password) {
        User user = getUser(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        log.info("Password of user: {} changed", user.getUsername());
    }

    @RolesAllowed("ROLE_ADMIN")
    public Role saveRole(Role role) {
        log.info("Saving role: " + role.getName());
        return roleRepository.save(role);
    }

    @RolesAllowed("ROLE_ADMIN")
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
        log.info("Role: {} added to user: {}", roleName, username);
    }
    @RolesAllowed("ROLE_ADMIN")
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
    @RolesAllowed("ROLE_ADMIN")
    public UserDto getUserDto(String username) {
        return new UserDto(userRepository.findByUsername(username));
    }

    @RolesAllowed("ROLE_ADMIN")
    public List<UserDto> getAllUsers() {
        log.info("getting all users!");
        return userRepository.findAll().stream().map((UserDto::new)).toList();
    }

    @RolesAllowed("ROLE_ADMIN")
    public void deleteUser(Long id) {
        User user = userRepository.getReferenceById(id);
        log.info("Deleting user: {}", user);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User not found in database");


        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public boolean isUserExisting(String username) {
        return getUser(username) != null;
    }
}
