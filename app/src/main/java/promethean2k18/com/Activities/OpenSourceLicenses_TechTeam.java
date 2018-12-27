package promethean2k18.com.Activities;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import promethean2k18.com.R;

public class OpenSourceLicenses_TechTeam extends AppCompatActivity {

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_team);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tech Team");

//        view = findViewById(R.id.view_something);
//
//        Element versionElement = new Element();
//        versionElement.setTitle("Version 2.0");
//        //versionElement.setIconDrawable(R.drawable.about);
//
//        View aboutPage = new AboutPage(this)
//                .setDescription("")
//                .setImage(R.drawable.logo)
//                .addItem(versionElement)
//                .addGroup("Connect with us")
//                .addEmail("promethean2k18@gmail.com")
//                .addWebsite("http://promethean2k18.com/")
//                .addPlayStore("https://play.google.com/store/apps/details?id=promethean2k18.com")
//                .addInstagram("https://www.instagram.com/promethean_2k18/?utm_source=ig_profile_share&igshid=3yckvov3ezqi")
//                .create();
//
//        setContentView(aboutPage);
//        Element versionElement2 = new Element();
//        versionElement2.setTitle("Android Developer");
//        //versionElement2.setIconDrawable(R.drawable.about);
//
//        Element versionElement3 = new Element();
//        versionElement3.setTitle("CSE III Year");
//        //versionElement3.setIconDrawable(R.drawable.about);
//
//        View fayaz = new AboutPage(this)
//                .setImage(R.drawable.fayaz)
//                .setDescription("Mohammad Fayaz\n(App Developer - Promethean 2k18)")
//                .addItem(versionElement2)
//                .addItem(versionElement3)
//                .addGroup("Connect with us")
//                .addEmail("fayaz07@gmail.com")
//                .addWebsite("http://fayaz01.github.io/")
//                .create();
////        setContentView(fayaz);
//        addContentView(fayaz,new ActionBar.LayoutParams(1));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
