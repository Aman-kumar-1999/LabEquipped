package com.labequiped.userservice.userservice.controller;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.labequiped.userservice.userservice.dto.UserRequest;
import com.labequiped.userservice.userservice.entities.BusinessType;
import com.labequiped.userservice.userservice.entities.Role;
import com.labequiped.userservice.userservice.entities.User;
import com.labequiped.userservice.userservice.repository.RoleRepository;
import com.labequiped.userservice.userservice.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());

        // encode password with BCrypt
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        // set business type
        BusinessType type = BusinessType.valueOf(req.getBusinessType().toUpperCase());
        user.setBusinessType(type);

        // assign roles
        Set<Role> roles = new HashSet<>();
        for (String roleName : req.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok("User created successfully");
    }
}

