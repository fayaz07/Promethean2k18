package promethean2k18.com.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import promethean2k18.com.R;

public class Communicate_with_us extends AppCompatActivity {

    Button mailUs, contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate_with_us);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Promethean 2k18");

        mailUs = findViewById(R.id.sendEmail);
        contactus = findViewById(R.id.callUs);

        mailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL , new
                        String[]{"admin@promethean2k18.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Contact support");
                emailIntent.putExtra(Intent.EXTRA_TEXT
                        , "");
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+91"+"9502039079"));
                if (ContextCompat.checkSelfPermission(Communicate_with_us.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Communicate_with_us.this, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else {
                    startActivity(callIntent);
                }
            }
        });

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
