package com.labequiped.userservice.userservice.dto;

import com.labequiped.userservice.userservice.entities.BusinessType;
import java.util.Set;

public class SidebarItemRequest {
    private String key;
    private String label;
    private String url;
    private boolean enabled;
    private Set<Long> roleIds; // IDs of roles allowed
    private BusinessType visibleFor;
    private Integer orderIndex;

    // getters/setters

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUrl(String url) {
        this.url = url;
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
