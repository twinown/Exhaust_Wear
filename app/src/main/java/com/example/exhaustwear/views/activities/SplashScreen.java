package com.example.exhaustwear.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.exhaustwear.views.activities.MainActivity;

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