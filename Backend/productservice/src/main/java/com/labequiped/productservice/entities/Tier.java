package com.labequiped.productservice.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Tier implements Serializable {
    @Id
    private String id;
    private String tierCode;
    private int productQuantity;
    private int productCost;
    private int offerPercentage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTierCode() {
        return tierCode;
    }

    public void setTierCode(String tierCode) {
        this.tierCode = tierCode;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductCost() {
        return productCost;
    }

    public void setProductCost(int productCost) {
        this.productCost = productCost;
    }

    public int getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(int offerPercentage) {
        this.offerPercentage = offerPercentage;
    }
}
