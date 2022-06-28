package com.example.exhaustwear.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // what opens after splashScreen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}