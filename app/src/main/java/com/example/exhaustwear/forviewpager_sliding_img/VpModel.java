package com.example.exhaustwear.forviewpager_sliding_img;

import android.net.Uri;

public class VpModel {
    private  int image;
    private Uri imageUri;

    public VpModel(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
