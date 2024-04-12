package com.psa.hustlex.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.psa.hustlex.R;


public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Intent intent  = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        },SPLASH_TIMEOUT);

    }
}
