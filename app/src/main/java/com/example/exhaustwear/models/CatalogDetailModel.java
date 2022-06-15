package com.example.exhaustwear.models;


public class CatalogDetailModel {
    String name;
    String img_url, img_url2, img_url3, img_url4;
    String price;
    String type;
    String group;
    String description;
    String size;
    String documentId;

    public CatalogDetailModel() {
    }


    public CatalogDetailModel(String name, String img_url, String img_url2, String img_url3, String img_url4,
                              String price, String type, String group, String description, String size, String documentId) {
        this.name = name;
        this.img_url = img_url;
        this.img_url2 = img_url2;
        this.img_url3 = img_url3;
        this.img_url4 = img_url4;
        this.price = price;
        this.type = type;
        this.group = group;
        this.description = description;
        this.size = size;
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_url2() {
        return img_url2;
    }

    public void setImg_url2(String img_url2) {
        this.img_url2 = img_url2;
    }

    public String getImg_url3() {
        return img_url3;
    }

    public void setImg_url3(String img_url3) {
        this.img_url3 = img_url3;
    }

    public String getImg_url4() {
        return img_url4;
    }

    public void setImg_url4(String img_url4) {
        this.img_url4 = img_url4;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


}
