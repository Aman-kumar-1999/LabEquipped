package com.labequiped.userservice.userservice.entities;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "SIDEBAR_ITEM")
public class SidebarItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIDEBAR_SEQ_GEN")
    @SequenceGenerator(name = "SIDEBAR_SEQ_GEN", sequenceName = "SIDEBAR_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ICON")
    private String icon; // internal key e.g. dashboard, orders, vendors

    @Column(name = "LABEL", nullable = false)
    private String label; // display label

    @Column(name = "href")
    private String href; // target route

    @Column(name = "IS_ENABLED")
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SIDEBAR_ROLE", joinColumns = @JoinColumn(name = "SIDEBAR_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> allowedRoles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "VISIBLE_FOR_BUSINESS")
    private BusinessType visibleFor = BusinessType.BOTH; // B2B, B2C, BOTH

    @Column(name = "ORDR")
    private Integer orderIndex = 0;

    // constructors, getters/setters
    public SidebarItem() {
    }

    public SidebarItem(String icon, String label, String href, boolean isEnabled, BusinessType visibleFor, Integer orderIndex) {
        this.icon = icon;
        this.label = label;
        this.href = href;
        this.isEnabled = isEnabled;
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