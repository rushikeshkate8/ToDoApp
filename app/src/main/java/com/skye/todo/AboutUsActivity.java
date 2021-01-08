package com.skye.todo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
                .setTitle( getResources().getString( R.string.our_other_app ) )
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
                .setTitle( getResources().getString( R.string.contact_us_through_email ) )
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
        develperNameElement.setTitle( getResources().getString( R.string.developer_name ) )
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
                .addGroup( getResources().getString( R.string.developer ) )
                .addItem(develperNameElement)
                .addItem( devPlayStorePageElement )
                .addGroup(getResources().getString( R.string.connect_with_us ))
                .addItem( emailElement )
                //.addEmail("rushikeshkate8@gmail.com", "Contact us through Email")
                //.addWebsite("https://mehdisakout.com/")
                .addFacebook("rushikesh.kate.35")
                //.addTwitter("medyo80")
                //.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                //.addPlayStore("com.ideashower.readitlater.pro")
                //.addGitHub("medyo")
                .addInstagram("rushikeshkate1")
                .addGroup( getResources().getString( R.string.app_version ) )
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

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

}