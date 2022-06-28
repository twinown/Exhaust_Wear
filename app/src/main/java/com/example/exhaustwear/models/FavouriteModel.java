package com.example.exhaustwear.models;

public class FavouriteModel {

    private String productImg;
    private String productName;
    private String productPrice;
    private String documentId;

    public FavouriteModel() {
    }

    public FavouriteModel(String productImg, String productName, String productPrice) {
        this.productImg = productImg;
        this.productName = productName;
        this.productPrice = productPrice;

    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
