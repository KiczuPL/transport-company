package com.example.transportcompany.controllers;

import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@Validated @RequestBody User user) {
        User result = userService.saveUser(user);
        if (result != null)
            return new ResponseEntity<User>(result, HttpStatus.CREATED);
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    public ResponseEntity<User> deleteUser(Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<User>(HttpStatus.OK);
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
    private String user;
    private String role;
}
