package com.labequiped.userservice.userservice.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
// import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.labequiped.userservice.userservice.dto.SidebarItemDTO;
import com.labequiped.userservice.userservice.dto.SidebarItemRequest;
// import com.labequiped.userservice.userservice.entities.BusinessType;
import com.labequiped.userservice.userservice.entities.Role;
import com.labequiped.userservice.userservice.entities.SidebarItem;
// import com.labequiped.userservice.userservice.entities.User;
import com.labequiped.userservice.userservice.repository.RoleRepository;
import com.labequiped.userservice.userservice.repository.SidebarItemRepository;

@Service
public class SidebarService {

    @Autowired
    private SidebarItemRepository sidebarRepository;

    @Autowired
    private RoleRepository roleRepository;

    // @Autowired
    // private SidebarItemRepository sidebarItemRepository;

    // public SidebarService(SidebarItemRepository sidebarItemRepository) {
    //     this.sidebarItemRepository = sidebarItemRepository;
    // }

    /**
     * Build sidebar for given user: filter items by user's roles and businessType.
     */
    // public List<SidebarItemDTO> getSidebarForUser(User user) {
    //     // eager roles on user entity, so user.getRoles() is available
    //     Set<String> userRoleNames = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());

    //     return sidebarItemRepository.findAllByIsEnabledTrueOrderByOrderIndexAsc().stream()
    //             .filter(item -> isVisibleToUser(item, userRoleNames, user.getBusinessType()))
    //             .map(this::toDto)
    //             .collect(Collectors.toList());
    // }

    // private boolean isVisibleToUser(SidebarItem item, Set<String> userRoleNames,
    //         BusinessType businessType) {
    //     // business check
    //     if (item.getVisibleFor() != null && item.getVisibleFor() != BusinessType.BOTH) {
    //         if (item.getVisibleFor() == BusinessType.B2B
    //                 && businessType != BusinessType.B2B)
    //             return false;
    //         if (item.getVisibleFor() == BusinessType.B2C
    //                 && businessType != BusinessType.B2C)
    //             return false;
    //     }

    //     // roles check: if item.allowedRoles is empty -> treat as public (visible to all
    //     // authenticated users)
    //     if (item.getAllowedRoles() == null || item.getAllowedRoles().isEmpty())
    //         return true;

    //     return item.getAllowedRoles().stream().map(r -> r.getName()).anyMatch(userRoleNames::contains);
    // }

    // private SidebarItemDTO toDto(SidebarItem item) {
    //     return new SidebarItemDTO(item.getKey(), item.getLabel(), item.getUrl(), item.getOrderIndex());
    // }

    // public SidebarService(SidebarRepository sidebarRepository, RoleRepository roleRepository) {
    //     this.sidebarRepository = sidebarRepository;
    //     this.roleRepository = roleRepository;
    // }

    public SidebarItem createSidebarItem(SidebarItemRequest request) {
        SidebarItem item = new SidebarItem();
        item.setKey(request.getKey());
        item.setLabel(request.getLabel());
        item.setUrl(request.getUrl());
        item.setEnabled(request.isEnabled());
        item.setVisibleFor(request.getVisibleFor());
        item.setOrderIndex(request.getOrderIndex());

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoleIds()));
        item.setAllowedRoles(roles);

        return sidebarRepository.save(item);
    }

    public SidebarItem updateSidebarItem(Long id, SidebarItemRequest request) {
        SidebarItem item = sidebarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sidebar item not found"));

        item.setKey(request.getKey());
        item.setLabel(request.getLabel());
        item.setUrl(request.getUrl());
        item.setEnabled(request.isEnabled());
        item.setVisibleFor(request.getVisibleFor());
        item.setOrderIndex(request.getOrderIndex());

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoleIds()));
        item.setAllowedRoles(roles);

        return sidebarRepository.save(item);
    }

    public void deleteSidebarItem(Long id) {
        sidebarRepository.deleteById(id);
    }

    public SidebarItem getSidebarItem(Long id) {
        return sidebarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sidebar item not found"));
    }

    public List<SidebarItem> getAllSidebarItems() {
        return sidebarRepository.findAll();
    }



}
