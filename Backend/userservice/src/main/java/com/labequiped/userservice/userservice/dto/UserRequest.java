package com.labequiped.userservice.userservice.dto;


import java.util.Set;

public class UserRequest {
    private String username;
    private String password;
    private Set<String> roles; // e.g. ["ROLE_USER", "ROLE_VENDOR"]
    private String businessType; // "B2B" | "B2C" | "BOTH"

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
}
