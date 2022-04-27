package com.example.exhaustwear.Model;

import java.io.Serializable;

public class StuffDetailModel  {
        String img_url;
        String price;
        String size;
        String description;

        public StuffDetailModel() {
        }

        public StuffDetailModel(String img_url, String price, String size, String description) {
                this.img_url = img_url;
                this.price = price;
                this.size = size;
                this.description = description;
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
}
