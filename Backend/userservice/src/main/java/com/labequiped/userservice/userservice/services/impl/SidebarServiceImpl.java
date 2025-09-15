package com.labequiped.userservice.userservice.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
// import java.util.stream.Collectors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.labequiped.userservice.userservice.dto.SidebarItemDTO;
// import com.labequiped.userservice.userservice.dto.SidebarItemDTO;
import com.labequiped.userservice.userservice.dto.SidebarItemRequest;
import com.labequiped.userservice.userservice.entities.BusinessType;
// import com.labequiped.userservice.userservice.entities.BusinessType;
import com.labequiped.userservice.userservice.entities.Role;
import com.labequiped.userservice.userservice.entities.SidebarItem;
import com.labequiped.userservice.userservice.entities.User;
// import com.labequiped.userservice.userservice.entities.User;
import com.labequiped.userservice.userservice.repository.RoleRepository;
import com.labequiped.userservice.userservice.repository.SidebarItemRepository;
import com.labequiped.userservice.userservice.repository.UserRepository;
import com.labequiped.userservice.userservice.services.SidebarService;

@Service
public class SidebarServiceImpl implements SidebarService {

    @Autowired
    private SidebarItemRepository sidebarRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private SidebarItemRepository sidebarItemRepository;

    /**
     * Build sidebar for given user: filter items by user's roles and businessType.
     */
    public List<SidebarItemDTO> getSidebarForUser(User user) {
        // eager roles on user entity, so user.getRoles() is available
        Set<String> userRoleNames = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());

        return sidebarRepository.findAllByIsEnabledTrueOrderByOrderIndexAsc().stream()
                .filter(item -> isVisibleToUser(item, userRoleNames, user.getBusinessType()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private boolean isVisibleToUser(SidebarItem item, Set<String> userRoleNames,
            BusinessType businessType) {
        // business check
        if (item.getVisibleFor() != null && item.getVisibleFor() != BusinessType.BOTH) {
            if (item.getVisibleFor() == BusinessType.B2B
                    && businessType != BusinessType.B2B)
                return false;
            if (item.getVisibleFor() == BusinessType.B2C
                    && businessType != BusinessType.B2C)
                return false;
        }

        // roles check: if item.allowedRoles is empty -> treat as public (visible to all
        // authenticated users)
        if (item.getAllowedRoles() == null || item.getAllowedRoles().isEmpty())
            return true;

        return item.getAllowedRoles().stream().map(r -> r.getName()).anyMatch(userRoleNames::contains);
    }
    //Long id, String icon, String label, String href, boolean isEnabled, Set<Role> allowedRoles, BusinessType visibleFor, Integer orderIndex
    private SidebarItemDTO toDto(SidebarItem item) {
        // return new SidebarItemDTO(
        //     item.getId(),
        //     item.getIcon(),
        //     item.getLabel(),
        //     item.getHref(),
        //     item.isEnabled(),
        //     item.getAllowedRoles().stream().map(Role::getName).collect(Collectors.toSet()),
        //     item.getVisibleFor(),
        //     item.getOrderIndex()
        // );
        SidebarItemDTO dto = new SidebarItemDTO();
        dto.setId(item.getId());
        dto.setIcon(item.getIcon());
        dto.setLabel(item.getLabel());
        dto.setHref(item.getHref());
        dto.setEnabled(item.isEnabled());
        dto.setAllowedRoles(item.getAllowedRoles());
        dto.setVisibleFor(item.getVisibleFor());
        dto.setOrderIndex(item.getOrderIndex());
        return dto;

    }

    public SidebarItem createSidebarItem(SidebarItemRequest request) {
        SidebarItem item = new SidebarItem();
        item.setIcon(request.getIcon());
        item.setLabel(request.getLabel());
        item.setHref(request.getHref());
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

        item.setIcon(request.getIcon());
        item.setLabel(request.getLabel());
        item.setHref(request.getHref());
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

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
