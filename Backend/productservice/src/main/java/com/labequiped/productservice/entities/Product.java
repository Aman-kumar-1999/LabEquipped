package com.labequiped.productservice.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

@Document(collection = "products")
public class  Product implements Serializable {

    @Id
    private String productId;

    private String vendorId;

    //private LocalDate date;

    private LocalDate createdAt;


    private String vendorName;

    private String vendorEmail;

    private String productName; // product Type also

    private String productDescription;
    private String category;

    private String brandName;

    private Integer productQuantity; // no. of products

    private float individualProductPrice;

    private float TotalProductPrice;

    private float natePriceWithDiscount;

    private float discountPercentage;

    private String status;

    private String action;

    private String imageName;

    private String imagePath;

    private String imageFileId;   // GridFS file id

    private String bulkCode;

    private String variationName;

    private String variationId;

    private float gst;

    private String hsn;

    private String isVerified;

    private String tierNo;

    private String containLiquid;

    private String companyCode;

    private String bulkPack;

    private float bulkPrice;

    public String getBulkPack() {
        return bulkPack;
    }

    public void setBulkPack(String bulkPack) {
        this.bulkPack = bulkPack;
    }

    public float getBulkPrice() {
        return bulkPrice;
    }

    public void setBulkPrice(float bulkPrice) {
        this.bulkPrice = bulkPrice;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getContainLiquid() {
        return containLiquid;
    }

    public void setContainLiquid(String containLiquid) {
        this.containLiquid = containLiquid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getIndividualProductPrice() {
        return individualProductPrice;
    }

    public void setIndividualProductPrice(float individualProductPrice) {
        this.individualProductPrice = individualProductPrice;
    }

    public float getTotalProductPrice() {
        return TotalProductPrice;
    }

    public void setTotalProductPrice(float totalProductPrice) {
        TotalProductPrice = totalProductPrice;
    }

    public float getNatePriceWithDiscount() {
        return natePriceWithDiscount;
    }

    public void setNatePriceWithDiscount(float natePriceWithDiscount) {
        this.natePriceWithDiscount = natePriceWithDiscount;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBulkCode() {
        return bulkCode;
    }

    public void setBulkCode(String bulkCode) {
        this.bulkCode = bulkCode;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public float getGst() {
        return gst;
    }

    public void setGst(float gst) {
        this.gst = gst;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getTierNo() {
        return tierNo;
    }

    public void setTierNo(String tierNo) {
        this.tierNo = tierNo;
    }

    public String getImageFileId() {
        return imageFileId;
    }
    public void setImageFileId(String imageFileId) {
        this.imageFileId = imageFileId;
    }
}
