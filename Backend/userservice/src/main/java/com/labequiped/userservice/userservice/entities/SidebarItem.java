package com.labequiped.userservice.userservice.entities;


import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "SIDEBAR_ITEM")
public class SidebarItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIDEBAR_SEQ_GEN")
    @SequenceGenerator(name = "SIDEBAR_SEQ_GEN", sequenceName = "SIDEBAR_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ITEM_KEY", nullable = false, unique = true)
    private String key; // internal key e.g. dashboard, orders, vendors

    @Column(name = "LABEL", nullable = false)
    private String label; // display label

    @Column(name = "URL")
    private String url; // target route

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

    public SidebarItem(String key, String label, String url, boolean isEnabled) {
        this.key = key;
        this.label = label;
        this.url = url;
        this.isEnabled = isEnabled;
        // this.visibleFor = visibleFor;
        // this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
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