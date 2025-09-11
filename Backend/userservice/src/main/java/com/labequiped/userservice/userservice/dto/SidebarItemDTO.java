package com.labequiped.userservice.userservice.dto;

public class SidebarItemDTO {
    private String key;
    private String label;
    private String url;
    private Integer orderIndex;

    public SidebarItemDTO() {
    }

    public SidebarItemDTO(String key, String label, String url, Integer orderIndex) {
        this.key = key;
        this.label = label;
        this.url = url;
        this.orderIndex = orderIndex;
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

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
