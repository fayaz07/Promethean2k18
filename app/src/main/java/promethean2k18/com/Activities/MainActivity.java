package promethean2k18.com.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import promethean2k18.com.Login_signup.CompleteProfile;
import promethean2k18.com.Login_signup.EmailAuth;
import promethean2k18.com.Login_signup.IntroActivity;
import promethean2k18.com.Login_signup.PhoneVerification;
import promethean2k18.com.R;
import promethean2k18.com.Utils.Urls;

public class MainActivity extends AppCompatActivity {

    private ImageView gmail,email,phone;
    private ProgressDialog progressDialog;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private NetworkInfo info;
    private ConnectivityManager connectivityManager;
    boolean isNetworkactive;
    LinearLayout continue_with_phone;
    ScrollView layout;
    Snackbar snackbar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        layout = findViewById(R.id.parentlayoutMainActivity);

        snackbar = Snackbar.make(layout,"Hello",Snackbar.LENGTH_INDEFINITE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        gmail = findViewById(R.id.continue_with_gmail);
        email = findViewById(R.id.continue_with_email);
        //phone = findViewById(R.id.continue_with_phone);
        continue_with_phone = findViewById(R.id.continue_with_phone_ll);

        mAuth = FirebaseAuth.getInstance();
        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        isNetworkactive = info!=null && info.isConnectedOrConnecting();

        button = findViewById(R.id.termsandconditions);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Open_Source_Licenses.class));
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Home_final.class));
            finish();
        }
        else {
            if (!isNetworkactive){
                Toast.makeText(getApplicationContext(),"No internet", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(MainActivity.this,IntroActivity.class));
        }

        //Google signin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END config_signin]



        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkactive){
                    Toast.makeText(getApplicationContext(),"No internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog();
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EmailAuth.class));
                finish();
            }
        });
        continue_with_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PhoneVerification.class));
                finish();
            }
        });
    }
//807068042837-7vlb9t5mfaf8a4tp2i1nif3v8urd9vdn.apps.googleusercontent.com

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // Toast.makeText(getApplicationContext(),"Signin failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Snackbar.make(layout, "Please be patient we are working on your request", Snackbar.LENGTH_INDEFINITE).show();
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            showMyProfileAsToast(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                            showMyProfileAsToast(null);
                        }
                    }
                });
        hideProgressDialog();
    }


    private void showMyProfileAsToast(FirebaseUser user) {
        if (user==null){
            Snackbar.make(layout, "Please try after sometime", Snackbar.LENGTH_LONG).show();
        }else{
            loginSuccess();
        }
    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideProgressDialog() {
        progressDialog.hide();
    }
    
    //Use this method to signout from google account
    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showMyProfileAsToast(null);
                    }
                });
    }

    //Use this method to revoke your account access
    private void revokeAccess() {
        mAuth.signOut();
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showMyProfileAsToast(null);
                    }
                });
    }


    private void loginSuccess(){
        //Check if user is existing and go to homepage if profile data is found
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.checknew)
                .addBodyParameter("uid",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ONRESPONSE",response);
                        if(response.trim().equals("new")) {
                            startActivity(new Intent(getApplicationContext(),CompleteProfile.class));
                            finish();
                        }else {
                            startActivity(new Intent(getApplicationContext(),Home_final.class));
                            finish();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d("ONRESPONSE",anError.toString());
                        Toast.makeText(getApplicationContext(), "Please try after some time", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
