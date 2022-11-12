package com.example.transportcompany.controllers;

import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.security.JwtConfig;
import com.example.transportcompany.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestBody String username){
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping("/role/addtouser")
   public ResponseEntity<Role> addRoleToUser(@RequestBody AssignRoleToUserForm form) {
        userService.addRoleToUser(form.getUser(),form.getRole());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
          return new ResponseEntity<Role>(userService.saveRole(role), HttpStatus.CREATED);
    }
}

@Data
class  AssignRoleToUserForm{
    private String user;
    private String role;
}
