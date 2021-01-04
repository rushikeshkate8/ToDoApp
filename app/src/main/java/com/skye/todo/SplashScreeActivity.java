package com.skye.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        EasySplashScreen config = new EasySplashScreen(this)
                //.withFullScreen()

                .withTargetActivity(MainActivity.class)

                .withSplashTimeOut(1000)

                .withBackgroundColor( Color.parseColor("#FFFFFF"))

                .withLogo( R.drawable.ic_baseline_done_24);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen );
    }
}