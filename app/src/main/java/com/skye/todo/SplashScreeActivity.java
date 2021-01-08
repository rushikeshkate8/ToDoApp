package com.skye.todo;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreeActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //darkmode();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        TypedValue typedValue = new TypedValue();
        Theme theme = this.getTheme();
        theme.resolveAttribute( R.attr.splashscreen_background_color, typedValue, true  );
        @ColorInt int splashScreenBackgroundColor = typedValue.data;

        EasySplashScreen config = new EasySplashScreen(this)
                //.withFullScreen()

                .withTargetActivity(MainActivity.class)

                .withSplashTimeOut(1000)

                .withBackgroundColor( splashScreenBackgroundColor )

                .withLogo( R.drawable.ic_baseline_task_alt);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen );
    }

    private void darkmode()
    {
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();

        boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode

        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }
    }
}