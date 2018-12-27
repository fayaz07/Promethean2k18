package promethean2k18.com.Login_signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import promethean2k18.com.R;
import promethean2k18.com.Utils.Urls;

public class Profile extends AppCompatActivity {

    private TextView name,email,phone,roll,college,uid,regevetns,dept,year;

    private NetworkInfo info;
    private ConnectivityManager connectivityManager;
    boolean isNetworkactive;

    private ProgressDialog progressDialog;
    private LoadToast loadToast;
//    private MyToastMessage myToastMessage;
    private RelativeLayout layout;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        layout = findViewById(R.id.parent_profile);
        loadToast = new LoadToast(this);
        loadToast.setText("Loading...");

        imageView = findViewById(R.id.userIdQR);

//        myToastMessage = new MyToastMessage(this);
//        myToastMessage.setMessage("Loading...");
        //myToastMessage.showMyToast();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        loadToast.setTranslationY(height/2+100);
        loadToast.setTextColor(Color.BLACK).
                setBackgroundColor(Color.WHITE).
                setProgressColor(Color.BLUE).
                setBorderColor(Color.GRAY);
        loadToast.show();

        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        isNetworkactive = info!=null && info.isConnectedOrConnecting();
//        if (!isNetworkactive){
//            Toast.makeText(getApplicationContext(),"Check your internet", Toast.LENGTH_SHORT).show();
//        }

        checkInternet();

        name = findViewById(R.id.nameProfile);
        name.setText("");
        email = findViewById(R.id.emailProfile);
        email.setText("");
        phone = findViewById(R.id.phoneProfile);
        phone.setText("");
        roll = findViewById(R.id.rollProfile);
        roll.setText("");
        college = findViewById(R.id.collegeProfile);
        college.setText("");
        uid = findViewById(R.id.uidProfile);
        uid.setText("");
        regevetns = findViewById(R.id.eventsRegProfile);
        regevetns.setText("");
        dept = findViewById(R.id.deptProfile);
        dept.setText("");
        year = findViewById(R.id.yearProfile);
        year.setText("");
    }

    private void checkInternet(){
        info = connectivityManager.getActiveNetworkInfo();
        isNetworkactive = info!=null && info.isConnected();
        if (isNetworkactive) {
           // myToastMessage.showMyToast();
            loadToast.show();
            loadData();
        }else {
            Snackbar.make(layout,"No internet",Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkInternet();
                }
            }).show();
            loadToast.error();
        }
    }

    private void loadData(){
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.retrieveuserdata)
                .addBodyParameter("uid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("user_data");

                            if (jsonArray.length()==0){
                                Toast.makeText(Profile.this, "OOPS! we are missing your data, please enter your details", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),CompleteProfile.class));
                                finish();
                            }

                            JSONObject j = jsonArray.getJSONObject(0);

                            name.setText(j.getString("name"));
                            uid.setText(j.getString("uid"));
                            email.setText(j.getString("email"));
                            phone.setText(j.getString("phone"));
                            dept.setText(j.getString("dept"));
                            roll.setText(j.getString("roll"));
                            year.setText(j.getString("year"));
                            college.setText(j.getString("college"));
                            regevetns.setText("Registered Events: " + j.getString("re"));
                            loadToast.success();

                            QRGEncoder encoder = new QRGEncoder(j.getString("uid"),null,QRGContents.Type.TEXT,512);
                            try {
                                Bitmap bitmap = encoder.encodeAsBitmap();
                                imageView.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                e.printStackTrace();;
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        //progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        // Log.d("error",""+anError);
                        Toast.makeText(getApplicationContext(),"Check your internet, avoid proxied wifi networks",Toast.LENGTH_SHORT).show();
                       // progressDialog.dismiss();
                        loadToast.error();
                        Snackbar.make(layout,"No internet",Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                checkInternet();
                            }
                        }).show();
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
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
    }
}
