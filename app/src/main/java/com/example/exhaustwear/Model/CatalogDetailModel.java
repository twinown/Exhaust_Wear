package com.example.exhaustwear.Model;

import java.io.Serializable;

public class CatalogDetailModel implements Serializable {
    String name;
    String img_url;
    String price;
    String type;
    String group;
    String description;

    public CatalogDetailModel() {
    }

    public CatalogDetailModel(String name, String img_url, String price, String type, String group, String description) {
        this.name = name;
        this.img_url = img_url;
        this.price = price;
        this.type = type;
        this.group = group;
        this.description = description;
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
