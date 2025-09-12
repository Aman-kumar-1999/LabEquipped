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
import java.util.stream.Collectors;

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

        user.setEmail(req.getEmail());

        user.setFirstName(req.getFirstName());

        user.setLastName(req.getLastName());

        if (req.getPassword() == null || req.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }
        if (req.getBusinessType() == null || req.getBusinessType().isEmpty()) {
            return ResponseEntity.badRequest().body("Business type cannot be empty");
        }
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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest req) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(req.getUsername());
            if (req.getPassword() != null && !req.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(req.getPassword()));
            }
            user.setBusinessType(BusinessType.valueOf(req.getBusinessType().toUpperCase()));
            Set<Long> roleIds = req.getRoles()
                       .stream()
                       .map(Long::valueOf) // convert String to Long
                       .collect(Collectors.toSet());
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
            user.setRoles(roles);

            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully");
        }).orElse(ResponseEntity.notFound().build());
        
    }
}