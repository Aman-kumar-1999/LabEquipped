package com.labequiped.userservice.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labequiped.userservice.userservice.entities.SidebarItem;

public interface SidebarItemRepository extends JpaRepository<SidebarItem, Long> {
    List<SidebarItem> findAllByIsEnabledTrueOrderByOrderIndexAsc();
    
}
