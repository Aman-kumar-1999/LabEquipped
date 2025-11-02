// package com.labequiped.userservice.userservice.others;

// import java.util.Set;

// import org.springframework.stereotype.Component;

// import com.labequiped.userservice.userservice.entities.BusinessType;
// import com.labequiped.userservice.userservice.entities.Role;
// import com.labequiped.userservice.userservice.entities.SidebarItem;
// import com.labequiped.userservice.userservice.repository.RoleRepository;
// import com.labequiped.userservice.userservice.repository.SidebarItemRepository;

// import jakarta.annotation.PostConstruct;

// @Component
// public class DataLoader {

//     private final RoleRepository roleRepository;
//     private final SidebarItemRepository sidebarItemRepository;

//     public DataLoader(RoleRepository roleRepository, SidebarItemRepository sidebarItemRepository) {
//         this.roleRepository = roleRepository;
//         this.sidebarItemRepository = sidebarItemRepository;
//     }

//     @PostConstruct
//     public void init() {
//         // create roles if missing
//         Role admin = roleRepository.findByName("ROLE_ADMIN")
//                 .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
//         Role superAdmin = roleRepository.findByName("ROLE_SUPER_ADMIN")
//                 .orElseGet(() -> roleRepository.save(new Role("ROLE_SUPER_ADMIN")));
//         Role vendor = roleRepository.findByName("ROLE_VENDOR")
//                 .orElseGet(() -> roleRepository.save(new Role("ROLE_VENDOR")));
//         Role buyer = roleRepository.findByName("ROLE_BUYER")
//                 .orElseGet(() -> roleRepository.save(new Role("ROLE_BUYER")));
//         Role user = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

//         // sample sidebar items
//         SidebarItem dash = new SidebarItem("dashboard", "Dashboard", "/dashboard", true);
//         dash.setOrderIndex(1);
//         dash.getAllowedRoles().add(admin);
//         dash.getAllowedRoles().add(superAdmin);
//         dash.getAllowedRoles().add(vendor);
//         dash.getAllowedRoles().add(buyer);
//         dash.getAllowedRoles().add(user);
//         dash.setVisibleFor(BusinessType.BOTH);

//         SidebarItem vendorPanel = new SidebarItem("vendor_panel", "Vendor Panel", "/vendor", true);
//         vendorPanel.setOrderIndex(5);
//         vendorPanel.getAllowedRoles().add(vendor);
//         vendorPanel.setVisibleFor(BusinessType.B2B);

//         sidebarItemRepository.saveAll(Set.of(dash, vendorPanel));
//     }
// }
