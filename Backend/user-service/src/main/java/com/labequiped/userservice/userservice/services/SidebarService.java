package com.labequiped.userservice.userservice.services;

import java.util.List;
import java.util.Optional;

import com.labequiped.userservice.userservice.dto.SidebarItemDTO;
import com.labequiped.userservice.userservice.dto.SidebarItemRequest;
import com.labequiped.userservice.userservice.entities.SidebarItem;
import com.labequiped.userservice.userservice.entities.User;

public interface SidebarService {
    List<SidebarItemDTO> getSidebarForUser(User user);
    Optional<User> findByUsername(String username);
    SidebarItem createSidebarItem(SidebarItemRequest request);
    SidebarItem updateSidebarItem(Long id, SidebarItemRequest request);
    void deleteSidebarItem(Long id);
    SidebarItem getSidebarItem(Long id);
    List<SidebarItem> getAllSidebarItems();

}
