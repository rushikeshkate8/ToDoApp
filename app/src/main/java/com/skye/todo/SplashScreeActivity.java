package com.skye.todo;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        TypedValue typedValue = new TypedValue();
        Theme theme = this.getTheme();
        theme.resolveAttribute( R.attr.splashscreen_background_color, typedValue, true  );
        @ColorInt int splashScreenBackgroundColor = typedValue.data;

        EasySplashScreen config = new EasySplashScreen(this)
                //.withFullScreen()

                .withTargetActivity(MainActivity.class)

                .withSplashTimeOut(1000)

                .withBackgroundColor( splashScreenBackgroundColor )

                .withLogo( R.drawable.ic_baseline_done_24);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen );
    }
}