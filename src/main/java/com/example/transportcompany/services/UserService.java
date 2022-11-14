package com.example.transportcompany.services;

import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.repositories.RoleRepository;
import com.example.transportcompany.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @RolesAllowed("ROLE_ADMIN")
    public User saveUser(User user) {
        User userByUsername = userRepository.findByUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userByUsername != null)
            throw new IllegalArgumentException("There already exists user with that username.");

        log.info("Saving user: " + user.toString());
        return userRepository.save(user);
    }

    @RolesAllowed("ROLE_ADMIN")
    public Role saveRole(Role role) {
        log.info("Saving role: " + role.getName());
        return roleRepository.save(role);
    }

    @RolesAllowed("ROLE_ADMIN")
    public void addRoleToUser(String username, String rolename) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
        userRepository.save(user);
        log.info("Role: {} added to user: {}", rolename, username);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @RolesAllowed("ROLE_ADMIN")
    public List<User> getAllUsers() {
        log.info("getting all users!");
        return userRepository.findAll();
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
}
