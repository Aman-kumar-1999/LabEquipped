package com.labequiped.userservice.userservice.dto;

import com.labequiped.userservice.userservice.entities.BusinessType;
import com.labequiped.userservice.userservice.entities.Role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SidebarItemDTO implements Serializable {
    private Long id;

    private String icon; // internal key e.g. dashboard, orders, vendors

    private String label; // display label

    private String href; // target route

    private boolean isEnabled;

    private Set<Role> allowedRoles = new HashSet<>();

    private BusinessType visibleFor = BusinessType.BOTH; // B2B, B2C, BOTH

    private Integer orderIndex = 0;

    public SidebarItemDTO() {
    }

    public SidebarItemDTO(Long id, String icon, String label, String href, boolean isEnabled, Set<Role> allowedRoles, BusinessType visibleFor, Integer orderIndex) {
        this.id = id;
        this.icon = icon;
        this.label = label;
        this.href = href;
        this.isEnabled = isEnabled;
        this.allowedRoles = allowedRoles;
        this.visibleFor = visibleFor;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<Role> getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(Set<Role> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    public BusinessType getVisibleFor() {
        return visibleFor;
    }

    public void setVisibleFor(BusinessType visibleFor) {
        this.visibleFor = visibleFor;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
