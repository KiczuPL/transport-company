package com.example.transportcompany.controllers;

import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dto.UserDto;
import com.example.transportcompany.model.forms.CreateUserForm;
import com.example.transportcompany.model.forms.UpdateUserForm;
import com.example.transportcompany.model.requests.UserRequest;
import com.example.transportcompany.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserInfo(HttpServletRequest request) {
        return new ResponseEntity<UserDto>(userService.getLoggedUserDto(request.getUserPrincipal().getName()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers(@RequestParam(required = false) String username,
                                                        @RequestParam(required = false) String firstName,
                                                        @RequestParam(required = false) String lastName,
                                                        @RequestParam(required = false) String email,
                                                        @RequestParam(required = false) String companyName,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        UserRequest request = new UserRequest(username, firstName, lastName, email, companyName, page, size);
        return new ResponseEntity<Map<String, Object>>(userService.getUsers(request), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody CreateUserForm form) {

        if (userService.isUserExisting(form.getUsername())) {

            return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);
        }


        UserDto result = userService.saveUser(form);
        return new ResponseEntity<UserDto>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateCompany(@Validated @RequestBody UpdateUserForm form) {
        return new ResponseEntity<UserDto>(userService.updateUser(form), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<UserDto>(HttpStatus.OK);
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<Role> addRoleToUser(@Validated @RequestBody AssignRoleToUserForm form) {
        userService.addRoleToUser(form.getUser(), form.getRole());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@Validated @RequestBody Role role) {
        return new ResponseEntity<Role>(userService.saveRole(role), HttpStatus.CREATED);
    }
}

@Data
class AssignRoleToUserForm {
    @NotNull
    @NotBlank
    private String user;
    @NotNull
    @NotBlank
    private String role;
}
