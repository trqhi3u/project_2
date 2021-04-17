/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Flynn
 */
public class Product {
    private int productID;
    private String productName;
    private float costPrice;
    private float sellingPrice;
    private String productImage;
    private String productCategory;
    private Status productStatus;
    private String date;
    private String productCode;

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductStatus(Status productStatus) {
        this.productStatus = productStatus;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public Status getProductStatus() {
        return productStatus;
    }

    public String getDate() {
        return date;
    }

}
