package com.example.exhaustwear.forviewpager_sliding_img;

import android.net.Uri;

public class VpModelHome {
    private  int image;
    private Uri imageUri;

    public VpModelHome(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
