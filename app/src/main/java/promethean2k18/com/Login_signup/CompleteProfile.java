package promethean2k18.com.Login_signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.steamcrafted.loadtoast.LoadToast;

import javax.net.ssl.SNIHostName;

import promethean2k18.com.Activities.Home_final;
import promethean2k18.com.R;
import promethean2k18.com.Data_models.Student_Profile_model;
import promethean2k18.com.Utils.InternetCheck;
import promethean2k18.com.Utils.Urls;

public class CompleteProfile extends AppCompatActivity {


    private EditText name,email,phone,college,roll;
    private Spinner year,dept;
    private CardView proceed;
    private ProgressDialog progressDialog;
    InternetCheck internetCheck;

    private LoadToast loadToast;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        internetCheck = new InternetCheck(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tell us about you");

        if (!internetCheck.isIsInternetAvailable()){
            Snackbar.make(layout,"No internet connection", Snackbar.LENGTH_SHORT).show();
        }

        layout = findViewById(R.id.complete_profile_parent);
        loadToast = new LoadToast(this);
        loadToast.setText("Loading...");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        loadToast.setTranslationY(height/2+100);
        loadToast.setTextColor(Color.BLACK).
                setBackgroundColor(Color.WHITE).
                setProgressColor(Color.BLUE).
                setBorderColor(Color.GRAY);

        AndroidNetworking.initialize(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        name = findViewById(R.id.nameCP);
        email = findViewById(R.id.emailCP);
        phone = findViewById(R.id.phoneCP);
        college = findViewById(R.id.collegeCP);
        roll = findViewById(R.id.rollCP);
        year = findViewById(R.id.year);
        dept = findViewById(R.id.dept);

        proceed = findViewById(R.id.proceedCP);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

    }

    private void saveUserData(){

        if (!internetCheck.isIsInternetAvailable()){
            Snackbar.make(layout,"No internet connection", Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveUserData();
                }
            }).show();
        }

        if (TextUtils.isEmpty(name.getText().toString())){
            name.setError("Invalid");
            return;
        }if (TextUtils.isEmpty(email.getText().toString())){
            email.setError("Invalid");
            return;
        }if (TextUtils.isEmpty(phone.getText().toString())){
            phone.setError("Invalid");
            return;
        }if (TextUtils.isEmpty(college.getText().toString())){
            college.setError("Invalid");
            return;
        }if (TextUtils.isEmpty(roll.getText().toString())){
            roll.setError("Invalid");
            return;
        }
        progressDialog.show();


        Student_Profile_model st = new Student_Profile_model();
        st.setName(name.getText().toString());
        st.setEmail(email.getText().toString());
        st.setPhone(phone.getText().toString());
        st.setCollege(college.getText().toString());
        st.setRoll(roll.getText().toString());
        st.setYear(year.getSelectedItem().toString());
        st.setDept(dept.getSelectedItem().toString());
        st.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        st.setRegEvents("0");

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Urls.saveuserdata)
                .addBodyParameter("name",name.getText().toString())
                .addBodyParameter("email",email.getText().toString())
                .addBodyParameter("phone",phone.getText().toString())
                .addBodyParameter("college",college.getText().toString())
                .addBodyParameter("roll",roll.getText().toString())
                .addBodyParameter("year",year.getSelectedItem().toString())
                .addBodyParameter("dept",dept.getSelectedItem().toString())
                .addBodyParameter("uid",FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .addBodyParameter("re","0")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ONRESPONSE",response);
                        Snackbar.make(layout, response, Snackbar.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(CompleteProfile.this,Home_final.class));
                        finish();
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d("ONRESPONSE",anError.getMessage());
                        Toast.makeText(CompleteProfile.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                        Snackbar.make(layout,"No internet",Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                checkInternet();
                            }
                        }).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void checkInternet(){

        if (internetCheck.isIsInternetAvailable()) {
            loadToast.show();
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
    }
}
