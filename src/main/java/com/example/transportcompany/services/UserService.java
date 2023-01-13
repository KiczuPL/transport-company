package com.example.transportcompany.services;

import com.example.transportcompany.email.EmailServiceImpl;
import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.model.dto.UserDto;
import com.example.transportcompany.model.forms.CreateUserForm;
import com.example.transportcompany.model.forms.UpdateUserForm;
import com.example.transportcompany.model.requests.UserRequest;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.RoleRepository;
import com.example.transportcompany.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.*;

import static com.example.transportcompany.security.RoleEnum.ROLE_USER;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

    @RolesAllowed({"ROLE_ADMIN"})
    public UserDto saveUserNoEmail(CreateUserForm form) {
        log.info("Saving user: " + form.toString());
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


        return new UserDto(userRepository.save(user));
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public UserDto saveUser(CreateUserForm form) {
        log.info("Saving user: " + form.toString());
        User userByUsername = userRepository.findByUsername(form.getUsername());
        Company company = companyRepository.findCompanyById(form.getCompanyId());
        if (userByUsername != null)
            throw new IllegalArgumentException("There already exists user with that username.");
        User userByEmail = userRepository.findByEmail(form.getEmail());
        if (userByEmail != null)
            throw new IllegalArgumentException("There already exists user with that email.");
        if (company == null)
            throw new IllegalArgumentException("No such company");

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        CharacterRule rule = new CharacterRule(EnglishCharacterData.Alphabetical);
        String password = passwordGenerator.generatePassword(10, rule);
        User user = new User(
                null,
                form.getUsername(),
                password,
                form.getEmail(),
                form.getFirstName(),
                form.getLastName(),
                companyRepository.getReferenceById(form.getCompanyId()),
                new ArrayList<>()
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.findByName(ROLE_USER.toString()));
        UserDto result = new UserDto(userRepository.save(user));
        emailService.sendCreateAccountMessage(form.getEmail(), form.getUsername(), password, company.getName());

        return result;
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public void changePassword(String username, String password) {
        User user = getUser(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        log.info("Password of user: {} changed", user.getUsername());
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public Role saveRole(Role role) {
        log.info("Saving role: " + role.getName());
        return roleRepository.save(role);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
        log.info("Role: {} added to user: {}", roleName, username);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public User getUser(String username) {
        log.info("Getting user {}", username);
        return userRepository.findByUsername(username);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public UserDto getUserDto(String username) {
        log.info("Getting user {}", username);
        return new UserDto(userRepository.findByUsername(username));
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public UserDto getLoggedUserDto(String username) {
        log.info("Getting user {}", username);
        return new UserDto(userRepository.findByUsername(username));
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public List<UserDto> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll().stream().map((UserDto::new)).toList();
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public void deleteUser(Long id) {
        User user = userRepository.getReferenceById(id);
        log.info("Deleting user: {}", user);
        userRepository.deleteById(id);
    }

    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public void changeUserPassword(String password,String username){
        User user = userRepository.findByUsername(username);
        log.info("User: {} requested to change password", user.getUsername());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        log.info("User: {} , password changed successfully", user.getUsername());
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

    @RolesAllowed({"ROLE_ADMIN"})
    public void deleteAllUsersFromCompany(Long id) {
        log.info("Deleting all users from company with id: {}", id);
        userRepository.deleteAllByCompanyId(id);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public Map<String, Object> getUsers(UserRequest request) {
        log.info("Handling get users request {}", request.toString());
        int pageNumber = request.getPageNumber() == null ? 0 : request.getPageNumber();
        int pageSize = request.getPageSize() == null ? 10 : request.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "username"));
        Page<User> pageUser = userRepository.findAllByUsernameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(
                request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getCompanyName(),
                pageRequest
        );
        Map<String, Object> response = new HashMap<>();
        response.put("users", pageUser.getContent().stream().toList().stream().map((UserDto::new)));
        response.put("currentPage", pageUser.getNumber());
        response.put("totalItems", pageUser.getTotalElements());
        response.put("totalPages", pageUser.getTotalPages());
        return response;
    }

    public UserDto updateUser(UpdateUserForm form) {
        Optional<User> userById = userRepository.findById(form.getId());
        if (userById.isEmpty())
            throw new IllegalArgumentException("No such user is present in database.");
        log.info("Updating user: {}  to: {}", userById.get().toString(), form.toString());
        User current = userById.get();
        Company company = companyRepository.getReferenceById(form.getCompanyId());
        User updated = new User(
                form.getId(),
                form.getUsername(),
                current.getPassword(),
                form.getEmail(),
                form.getFirstName(),
                form.getLastName(),
                company,
                current.getRoles()
        );
        return new UserDto(userRepository.save(updated));
    }
}
