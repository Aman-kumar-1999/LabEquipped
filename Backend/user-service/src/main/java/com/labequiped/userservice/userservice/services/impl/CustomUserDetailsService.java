package com.labequiped.userservice.userservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.labequiped.userservice.userservice.entities.User;
import com.labequiped.userservice.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
// import com.labequiped.userservice.userservice.services.UserDetailsService;
// import org.springframework.security.core.userdetails.UserDetailsService; 

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    public UserDetails loadUserByUsername(String username) {
        // Implementation here

         User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // already hashed
                .authorities(user.getRoles().stream().map(r -> r.getName()).toArray(String[]::new))
                .build();
    }

    public void updateUserDetails(UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Update fields as necessary
        user.setPassword(userDetails.getPassword()); // Ensure password is hashed before setting
        // Update other fields if needed

        userRepo.save(user);
    }

    
    
}
