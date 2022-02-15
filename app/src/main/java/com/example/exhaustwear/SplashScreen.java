package com.example.exhaustwear;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

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