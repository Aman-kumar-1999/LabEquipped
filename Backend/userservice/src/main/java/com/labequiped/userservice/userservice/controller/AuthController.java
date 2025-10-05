package com.labequiped.userservice.userservice.controller;


import com.labequiped.userservice.userservice.dto.SidebarItemDTO;
import com.labequiped.userservice.userservice.entities.SidebarItem;
import com.labequiped.userservice.userservice.entities.User;
import com.labequiped.userservice.userservice.others.AuthResponse;
import com.labequiped.userservice.userservice.repository.UserRepository;
import com.labequiped.userservice.userservice.services.impl.SidebarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.labequiped.userservice.userservice.security.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SidebarServiceImpl sidebarService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
//    @Cacheable(value = "loginCache", key = "#request['username']")
    @CachePut(value = "loginCache", key = "#request['username']")
    public <T> AuthResponse login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(userDetails.getUsername());
        // load full User entity to obtain roles and business type
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SidebarItemDTO> sidebar = sidebarService.getSidebarForUser(user);
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        AuthResponse.UserDetailsWrapper userDetailsWrapper = new AuthResponse.UserDetailsWrapper();
        userDetailsWrapper.setSidebar(sidebar);
        userDetailsWrapper.setUser(user);
        response.setUserDetailsWrapper(userDetailsWrapper);
//        Map<String,Object> response = Map.of(
//                "sidebar", sidebar,
//                "user", Map.of(
//                        "id", user.getId(),
//                        "username", user.getUsername(),
//                        "email", user.getEmail(),
//                        "firstName", user.getFirstName(),
//                        "lastName", user.getLastName(),
//                        "businessType", user.getBusinessType(),
//                        "roles", user.getRoles().stream().map(role -> Map.of(
//                                "id", role.getId(),
//                                "name", role.getName(),
//                                "isEnabled", role.isEnabled()
//                        )).collect(Collectors.toSet())
//                )
//        );
//        return ResponseEntity.ok(response);


//        Map<String,Object> map = new HashMap<>();
//        map.put("token", token);
//        map.put("user_details", response);
        return response;
    }


    @PutMapping("/login-update")
    @CachePut(value = "loginCache", key = "#request['username']")
    public <T> AuthResponse updateLoginDetails(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(userDetails.getUsername());
        // load full User entity to obtain roles and business type
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SidebarItemDTO> sidebar = sidebarService.getSidebarForUser(user);
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        AuthResponse.UserDetailsWrapper userDetailsWrapper = new AuthResponse.UserDetailsWrapper();
        userDetailsWrapper.setSidebar(sidebar);
        userDetailsWrapper.setUser(user);
        response.setUserDetailsWrapper(userDetailsWrapper);
//        Map<String,Object> response = Map.of(
//                "sidebar", sidebar,
//                "user", Map.of(
//                        "id", user.getId(),
//                        "username", user.getUsername(),
//                        "email", user.getEmail(),
//                        "firstName", user.getFirstName(),
//                        "lastName", user.getLastName(),
//                        "businessType", user.getBusinessType(),
//                        "roles", user.getRoles().stream().map(role -> Map.of(
//                                "id", role.getId(),
//                                "name", role.getName(),
//                                "isEnabled", role.isEnabled()
//                        )).collect(Collectors.toSet())
//                )
//        );
//        return ResponseEntity.ok(response);


//        Map<String,Object> map = new HashMap<>();
//        map.put("token", token);
//        map.put("user_details", response);
        return response;
    }

}
