package com.example.exhaustwear.models;



public class CatalogModel {

    String name;
    String img_url;
    String type;
    String documentId;



    public CatalogModel() {
    }

    public CatalogModel(String name, String img_url, String type) {
        this.name = name;
        this.img_url = img_url;
        this.type = type;

    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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




}
