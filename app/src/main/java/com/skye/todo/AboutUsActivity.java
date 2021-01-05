package com.skye.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        boolean nightmode = false;

        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/dev?id=6098242075383052047"));
        Element devPlayStorePageElement = new Element();
        devPlayStorePageElement.setIconDrawable( R.drawable.ic_baseline_apps_24 )
                .setTitle( "Our other Apps" )
                .setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity( playStoreIntent );
                    }
                } );

        Element emailElement = new Element();
        Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
        intentEmail.putExtra( Intent.EXTRA_EMAIL, new String[]{"rushikeshkate8@gmail.com"} );
        intentEmail.setData( Uri.parse( "mailto:" ) );
        emailElement.setIconDrawable( R.drawable.ic_baseline_email_24 )
                .setTitle( "Contact us through Email" )
                .setIntent( intentEmail );

        String version = "";
        PackageInfo packageInfo = null;
        try {
            packageInfo = this.getPackageManager().getPackageInfo( this.getPackageName(), 0 );
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Element versionElement = new Element();
        versionElement.setTitle( "v" + version );

        Element develperNameElement = new Element();
        develperNameElement.setTitle( "Rushikesh Kate" )
                           .setIconDrawable( R.drawable.ic_baseline_android_24 );

        int nightFlags = this.getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        if(nightFlags == Configuration.UI_MODE_NIGHT_YES)
            nightmode = true;

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .enableDarkMode(nightmode)
                //.setCustomFont(String) // or Typeface
                .setDescription( "ToDo App" )
                .setImage(R.drawable.ic_baseline_task_alt)
                .addGroup( "Developer" )
                .addItem(develperNameElement)
                .addItem( devPlayStorePageElement )
                .addGroup("Connect with us")
                .addItem( emailElement )
                //.addEmail("rushikeshkate8@gmail.com", "Contact us through Email")
                //.addWebsite("https://mehdisakout.com/")
                .addFacebook("rushikesh.kate.35")
                //.addTwitter("medyo80")
                //.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                //.addPlayStore("com.ideashower.readitlater.pro")
                //.addGitHub("medyo")
                .addInstagram("rushikeshkate1")
                .addGroup( "App Version" )
                .addItem(versionElement)
                .create();
        setContentView( aboutPage);

        getSupportActionBar().setHomeAsUpIndicator( R.drawable.ic_baseline_arrow_back_24 );
        String appName = " " + getSupportActionBar().getTitle().toString();
        if(nightmode)
            getSupportActionBar().setTitle( Html.fromHtml(  "<font color= '#FFFFFF'>"  + appName + "</font>" ));
        else
            getSupportActionBar().setTitle( Html.fromHtml(  "<font color= '#757575'>"  + appName + "</font>" ));

    }
}