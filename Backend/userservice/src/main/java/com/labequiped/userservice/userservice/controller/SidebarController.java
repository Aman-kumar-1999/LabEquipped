package com.labequiped.userservice.userservice.controller;

import com.labequiped.userservice.userservice.dto.SidebarItemDTO;
import com.labequiped.userservice.userservice.dto.SidebarItemRequest;
import com.labequiped.userservice.userservice.entities.SidebarItem;
import com.labequiped.userservice.userservice.entities.User;
import com.labequiped.userservice.userservice.services.impl.SidebarServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sidebar")
public class SidebarController {

    
    @Autowired
    private SidebarServiceImpl sidebarService;


    @PostMapping
    public ResponseEntity<SidebarItem> createSidebarItem(@RequestBody SidebarItemRequest request) {
        return ResponseEntity.ok(sidebarService.createSidebarItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SidebarItem> updateSidebarItem(@PathVariable Long id,
            @RequestBody SidebarItemRequest request) {
        return ResponseEntity.ok(sidebarService.updateSidebarItem(id, request));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSidebarItem(@RequestParam Long id) {
        sidebarService.deleteSidebarItem(id);
        return ResponseEntity.ok("Sidebar item deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<SidebarItem> getSidebarItem(@PathVariable Long id) {
        return ResponseEntity.ok(sidebarService.getSidebarItem(id));
    }

    // @GetMapping
    // public ResponseEntity<List<SidebarItem>> getAllSidebarItems() {
    //     return ResponseEntity.ok(sidebarService.getAllSidebarItems());
    // }

    @GetMapping
    public ResponseEntity<List<SidebarItemDTO>> getSidebar(@AuthenticationPrincipal UserDetails principal) {
        // principal might be null for anonymous; adapt to your security setup
        if (principal == null)
            return ResponseEntity.status(401).build();

        // load full User entity to obtain roles and business type
        User user = sidebarService.findByUsername(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SidebarItemDTO> sidebar = sidebarService.getSidebarForUser(user);
        return ResponseEntity.ok(sidebar);
    }

}

    // import java.util.List;

    // import org.springframework.beans.factory.annotation.Autowired;
    // import org.springframework.http.ResponseEntity;
    // import org.springframework.security.core.annotation.AuthenticationPrincipal;
    // import org.springframework.security.core.userdetails.UserDetails;
    // import org.springframework.web.bind.annotation.GetMapping;
    // import org.springframework.web.bind.annotation.RequestMapping;
    // import org.springframework.web.bind.annotation.RestController;

    // import com.labequiped.userservice.userservice.dto.SidebarItemDTO;
    // import com.labequiped.userservice.userservice.entities.User;
    // import com.labequiped.userservice.userservice.repository.UserRepository;
    // import com.labequiped.userservice.userservice.services.SidebarService;

    // @RestController
    // @RequestMapping("/api/sidebar")
    // public class SidebarController {

    // @Autowired
    // private SidebarService sidebarService;

    // @Autowired
    // private UserRepository userRepository;

    // // public SidebarController(SidebarService sidebarService, UserRepository
    // userRepository) {
    // // this.sidebarService = sidebarService;
    // // this.userRepository = userRepository;
    // // }

    

// }
