package com.labequiped.userservice.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.labequiped.userservice.userservice.dto.RoleRequest;
import com.labequiped.userservice.userservice.entities.Role;
import com.labequiped.userservice.userservice.repository.RoleRepository;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;


    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
        if (roleRepository.findByName(request.getName().toUpperCase()).isPresent()) {
            return ResponseEntity.badRequest().body("Role already exists");
        }

        Role role = new Role(request.getName().toUpperCase(), request.isEnabled());
        roleRepository.save(role);

        return ResponseEntity.ok("Role created successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
