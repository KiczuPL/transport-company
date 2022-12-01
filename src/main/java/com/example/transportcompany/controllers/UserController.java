package com.example.transportcompany.controllers;

import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dto.UserDto;
import com.example.transportcompany.model.forms.CreateUserForm;
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
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserInfo( HttpServletRequest request) {
        return new ResponseEntity<UserDto>(userService.getLoggedUserDto(request.getUserPrincipal().getName()), HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserDto> saveUser(@Validated @RequestBody CreateUserForm form) {

        if (!userService.isUserExisting(form.getUsername()))
            return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);

        UserDto result = userService.saveUser(form);
        return new ResponseEntity<UserDto>(result, HttpStatus.CREATED);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUserDto(username));
    }

    @DeleteMapping("/user")
    public ResponseEntity<UserDto> deleteUser(Long id) {
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
