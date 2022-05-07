package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    RoleRepository roleRepository;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllusers() {
        List<User> users = userService.listAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.get(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/add/{roleName}")
    public ResponseEntity<User> addUser(@PathVariable("roleName") String roleName,@RequestBody User user) throws Exception {
        Set<Role> roles = new HashSet<>();


        if (roleName == null) {
            Role userRole = roleRepository.findByName("ROLE_STUDENT");
            roles.add(userRole);
        } else {
            if(roleName.equals("ROLE_ADMIN")) {
                Role userRole = roleRepository.findByName("ROLE_ADMIN");
                roles.add(userRole);
            } else if(roleName.equals("ROLE_TEACHER")) {
                Role userRole = roleRepository.findByName("ROLE_TEACHER");
                roles.add(userRole);
            } else {
                Role userRole = roleRepository.findByName("ROLE_STUDENT");
                roles.add(userRole);
            }
        }

        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update/{roleName}")
    public ResponseEntity<User> updateUser(@PathVariable("roleName") String roleName,@RequestBody User user) throws Exception {
        Set<Role> roles = new HashSet<>();

        if (roleName == null) {
            Role userRole = roleRepository.findByName("ROLE_STUDENT");
            roles.add(userRole);
        } else {
            if(roleName.equals("ROLE_ADMIN")) {
                Role userRole = roleRepository.findByName("ROLE_ADMIN");
                roles.add(userRole);
            } else if(roleName.equals("ROLE_TEACHER")) {
                Role userRole = roleRepository.findByName("ROLE_TEACHER");
                roles.add(userRole);
            } else {
                Role userRole = roleRepository.findByName("ROLE_STUDENT");
                roles.add(userRole);
            }
        }

        user.setRoles(roles);

        if(user.getPassword().length()<=12) {
            userService.save(user);
        } else {
            userService.update(user);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/updateDetails")
    public ResponseEntity<User> updateUserDetails(@RequestBody User user) throws Exception {
        User oldUser = userService.getUserByEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setImageUrl(user.getImageUrl());
        oldUser.setUsername(user.getUsername());

        User updateUser = null;
        if(user.getPassword().length()<=12) {
            updateUser =  userService.save(oldUser);
        } else {
             updateUser = userService.update(oldUser);
        }

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}