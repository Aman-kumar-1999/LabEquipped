package com.labequiped.userservice.userservice.dto;

import com.labequiped.userservice.userservice.entities.BusinessType;
import java.util.Set;

public class SidebarItemRequest {
    private String icon;
    private String label;
    private String href;
    private boolean enabled;
    private Set<Long> roleIds; // IDs of roles allowed
    private BusinessType visibleFor;
    private Integer orderIndex;

    // getters/setters

    public String getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }

    public String getHref() {
        return href;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public BusinessType getVisibleFor() {
        return visibleFor;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public void setVisibleFor(BusinessType visibleFor) {
        this.visibleFor = visibleFor;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
