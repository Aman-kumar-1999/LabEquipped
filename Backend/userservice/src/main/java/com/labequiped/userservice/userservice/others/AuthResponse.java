package com.labequiped.userservice.userservice.others;


import com.labequiped.userservice.userservice.dto.SidebarItemDTO;
import com.labequiped.userservice.userservice.entities.SidebarItem;
import com.labequiped.userservice.userservice.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class AuthResponse implements Serializable {

    private UserDetailsWrapper userDetailsWrapper;
    private String token;

    public AuthResponse() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponse that = (AuthResponse) o;
        return Objects.equals(userDetailsWrapper, that.userDetailsWrapper) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDetailsWrapper, token);
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "user_details=" + userDetailsWrapper +
                ", token='" + token + '\'' +
                '}';
    }

    public AuthResponse(UserDetailsWrapper userDetailsWrapper, String token) {
        this.userDetailsWrapper = userDetailsWrapper;
        this.token = token;
    }

    public static class UserDetailsWrapper implements Serializable {
        private User user;
        private List<SidebarItemDTO> sidebar;

        // Getters and Setters
        public User getUser() {
            return user;
        }
        public void setUser(User user) {
            this.user = user;
        }
        public List<SidebarItemDTO> getSidebar() {
            return sidebar;
        }
        public void setSidebar(List<SidebarItemDTO> sidebar) {
            this.sidebar = sidebar;
        }

        public UserDetailsWrapper(User user, List<SidebarItemDTO> sidebar) {
            this.user = user;
            this.sidebar = sidebar;
        }

        public UserDetailsWrapper() {
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            UserDetailsWrapper that = (UserDetailsWrapper) o;
            return Objects.equals(user, that.user) && Objects.equals(sidebar, that.sidebar);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, sidebar);
        }

        @Override
        public String toString() {
            return "UserDetailsWrapper{" +
                    "user=" + user +
                    ", sidebar=" + sidebar +
                    '}';
        }
    }

    public static class AllowedRole implements Serializable {
        private Long id;
        private String name;
        private Boolean enabled;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public AllowedRole() {
        }

        public AllowedRole(Long id, String name, Boolean enabled) {
            this.id = id;
            this.name = name;
            this.enabled = enabled;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            AllowedRole that = (AllowedRole) o;
            return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(enabled, that.enabled);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, enabled);
        }

        @Override
        public String toString() {
            return "AllowedRole{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    // Getters and Setters
    public UserDetailsWrapper getUser_details() {
        return userDetailsWrapper;
    }
    public void setUserDetailsWrapper(UserDetailsWrapper userDetailsWrapper) {
        this.userDetailsWrapper = userDetailsWrapper;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;

    }


}

