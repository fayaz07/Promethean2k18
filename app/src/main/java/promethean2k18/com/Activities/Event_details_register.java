package promethean2k18.com.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import promethean2k18.com.Data_models.Events_model;
import promethean2k18.com.Data_models.Student_Profile_model;
import promethean2k18.com.Login_signup.CompleteProfile;
import promethean2k18.com.R;
import promethean2k18.com.Utils.InternetCheck;
import promethean2k18.com.Utils.Urls;

public class Event_details_register extends AppCompatActivity {

    ImageView imageView, callOne, callTwo;
    Intent intent;
    String id,type;
    Events_model events_details;
    ProgressDialog progressDialog;
    TextView organisingdept, eventname, eventdesc, rules, participants, price, co1, co2;
    ProgressBar progressBar;
    EditText refer;
    CardView registerButton;
    Student_Profile_model profile_model;
    ScrollView layout;
    InternetCheck internetCheck;
    RadioGroup radioGroup;
    EditText teammatesNames;
    boolean isDataLoaded;
    int participationType=-1,numberOfParticipants=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Register");

        layout = findViewById(R.id.parentScrollViewEventRegister);
        internetCheck = new InternetCheck(this);

        teammatesNames = findViewById(R.id.teammatesNames);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");

        Log.i("event_id",id);

        radioGroup = findViewById(R.id.radioGroupSelectTeamSize);

        refer = findViewById(R.id.referrer);
        events_details = new Events_model();
        imageView = findViewById(R.id.event_poster);
        organisingdept = findViewById(R.id.event_organised_by_dept);
        eventdesc = findViewById(R.id.event_desc);
        eventname = findViewById(R.id.event_name);
        rules = findViewById(R.id.event_rules);
        participants = findViewById(R.id.event_details_participants);
        price = findViewById(R.id.event_details_amount);
        co1 = findViewById(R.id.event_coo_1);
        co2 = findViewById(R.id.event_coo_2);
        progressBar = findViewById(R.id.progressBarImage);
        progressBar.setIndeterminate(true);
        registerButton = findViewById(R.id.proceedCP);
        callOne = findViewById(R.id.callOne);
        callTwo = findViewById(R.id.callTwo);

        profile_model = new Student_Profile_model();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataLoaded && internetCheck.isIsInternetAvailable()) {
                    if(Integer.parseInt(events_details.getPrice_team())==0 && Integer.parseInt(events_details.getPrice())==0){
                        Snackbar.make(layout,"Sorry, we haven't finalized the registration fee, please try later",Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    registerDialog();
                }
                else
                    Snackbar.make(layout,"Something has gone wrong, please try later",Snackbar.LENGTH_SHORT).show();

            }
        });

        callOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(events_details.getCo1Phone())){
                    call(events_details.getCo1Phone());
                }
            }
        });
        callTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(events_details.getCo2Phone())){
                    call(events_details.getCo2Phone());
                }
            }
        });

        if (!internetCheck.isIsInternetAvailable()) {
            Snackbar.make(layout, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                participationType = radioGroup.getCheckedRadioButtonId();
                if (participationType%2==0) {
                    teammatesNames.setEnabled(true);
                    teammatesNames.requestLayout();
                }else{
                    teammatesNames.setEnabled(false);
                    teammatesNames.requestLayout();
                }

                Log.i("radiocheck",String.valueOf(participationType));
            }
        });

        AndroidNetworking.initialize(this);
        loadUserData();
    }


    private void loadUserData(){
        if (!internetCheck.isIsInternetAvailable()){
            Snackbar.make(layout,"We couldn't find a working internet connection",Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadUserData();
                }
            });
            return;
        }
        loadData();
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
                                Toast.makeText(getApplicationContext(), "OOPS! we are missing your profile data, please enter your details and try again", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),CompleteProfile.class));
                                finish();
                            }
                            JSONObject j = jsonArray.getJSONObject(0);

                            profile_model.setName(j.getString("name"));
                            profile_model.setUid(j.getString("uid"));
                            profile_model.setDept(j.getString("dept"));
                            profile_model.setYear(j.getString("year"));
                            profile_model.setRoll(j.getString("roll"));
                            profile_model.setCollege(j.getString("college"));
                            profile_model.setEmail(j.getString("email"));
                            profile_model.setPhone(j.getString("phone"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onError(ANError anError) {
                        Snackbar.make(layout, "Something's wrong, retrieving your profile, please try after some time", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    public void call(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+91"+number));
        if (ContextCompat.checkSelfPermission(Event_details_register.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Event_details_register.this, new String[]{Manifest.permission.CALL_PHONE},1);
        }
        else {
            startActivity(callIntent);
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
      //  startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
    }

    private void loadData(){
        Log.d("details","loading started");

        String url = "";
        progressDialog.show();
        if (type.equals("f")){
            url = Urls.retrieveeventfbyid;
        }else if (type.equals("n"))
            url = Urls.retrieveeventbyid;
        else
            url = Urls.getNonTechEventsById;


        Log.d("details","initialized fan");
        AndroidNetworking.post(url)
                .addBodyParameter("id",id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("details","onresponse of fan");
                        progressDialog.show();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("events");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Snackbar.make(layout, "Sorry, something has gone wrong there, please check back  later", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                }).show();
                                return;
                            }
                            //while (count < jsonArray.length()) {
                                Log.d("details","events details array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(0);

                                events_details.setEventId(j.getString("id").trim());
                                events_details.setEventName(j.getString("name").trim());
                                events_details.setOrganisingDept(j.getString("dept").trim());
                                events_details.setDesc(j.getString("e_desc").trim());
                                events_details.setRules(j.getString("rules").trim());
                                events_details.setTags(j.getString("participants").trim());
                                events_details.setPrice(j.getString("price").trim());
                                events_details.setCo1(j.getString("co_one").trim());
                                events_details.setCo2(j.getString("co_two").trim());
                                events_details.setCo1Phone(j.getString("co_one_phone").trim());
                                events_details.setCo2Phone(j.getString("co_two_phone").trim());
                                events_details.setImageUrl(j.getString("url").trim());
                                events_details.setType(j.getString("type").trim());
                                events_details.setE_email(j.getString("e_email").trim());
                                events_details.setPrice_team(j.getString("team_price").trim());

                                //organisingdept, eventname, eventdesc, rules, participants, price, co1, co2
                                organisingdept.setText(j.getString("dept").trim());
                                eventname.setText(j.getString("name").trim());
                                eventdesc.setText(j.getString("e_desc").trim());
                                rules.setText(j.getString("rules").trim());
                                participants.setText("Tags: " + j.getString("participants").trim());

                                numberOfParticipants = Integer.parseInt(events_details.getType());

                                if (Integer.parseInt(events_details.getPrice()) != 0 && numberOfParticipants==1){
                                    //1 participant only
                                    price.setText("Registration Fee : \nfor individual Rs. " + j.getString("price").trim());
                                    radioGroup.getChildAt(1).setEnabled(false);
                                    teammatesNames.setEnabled(false);
                                }else if (Integer.parseInt(events_details.getPrice()) != 0 && numberOfParticipants>1){
                                    //multiple participants, 1 or more
                                    price.setText("Registration Fee : \nfor Individual Rs. " + j.getString("price").trim() +
                                            "\nfor Team Rs. " + j.getString("team_price").trim() );
                                }else{
                                    //only group 2 or more
                                    price.setText("Registration Fee : "+
                                            "\nfor Team Rs. " + j.getString("team_price").trim() );
                                    radioGroup.getChildAt(0).setEnabled(false);
                                }

//                                if (Integer.parseInt(events_details.getPrice()) != 0 && Integer.parseInt(events_details.getType())==1){
//                                    price.setText("Registration Fee : Rs. " + j.getString("price").trim());
//                                    radioGroup.getChildAt(1).setEnabled(false);
//                                    teammatesNames.setEnabled(false);
//                                }else if(Integer.parseInt(events_details.getPrice()) != 0 && Integer.parseInt(events_details.getType())>1){
//                                    price.setText("Individual Registration Fee : Rs. " + j.getString("price").trim() +
//                                            "\nTeam Registration Fee : Rs. " + events_details.getPrice_team().trim());
//                                }else{
//                                    price.setText("Team Registration Fee : Rs. " + events_details.getPrice_team().trim());
//                                    radioGroup.getChildAt(0).setEnabled(false);
//                                }
//

                                co1.setText("Co-ordinator 1:  " + j.getString("co_one").trim()+"  ");
                                co2.setText("Co-ordinator 2:  " + j.getString("co_two").trim()+"  ");

                                Picasso.get().load(j.getString("url").trim())
                                        .fit()
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_event_available_black_24dp));
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                                progressDialog.dismiss();
                                isDataLoaded = true;
                        }catch (JSONException e){
                            Log.d("tag","exception " + e.getMessage());
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Snackbar.make(layout,"Something's gone wrong, please try after sometime",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag","anerror " +anError.getResponse()+anError.getErrorBody());
                        progressDialog.dismiss();
                        Snackbar.make(layout,"Check your internet, avoid proxied wifi networks",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected static SecureRandom random = new SecureRandom();

    public synchronized String generateToken( String username ) {
        long longToken = Math.abs( random.nextLong() );
        String random = Long.toString( longToken, 16 );
        return  random;
    }

    private void registerDialog(){
        final String name = profile_model.getName();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String phone = profile_model.getPhone();
        final String email = profile_model.getEmail();
        final String referrer = refer.getText().toString();

        final String regToken = generateToken(name);

        String teammates;
        if (participationType==-1){
            Snackbar.make(layout,"Select particiaption type",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (numberOfParticipants>1 && participationType%2==0 && TextUtils.isEmpty(teammatesNames.getText())) {
            teammatesNames.setError("Enter teammates");
            return;
        }
        if (!isDataLoaded){
            Snackbar.make(layout,"Poor internet connection, please try after sometime",Snackbar.LENGTH_SHORT).show();
            return;
        }

        String data="";
        int casee=0;
        if (participationType%2==0){
            casee=2;
        }else{
            casee=1;
        }

        switch (casee){
            case 1: data = "Are you sure " + name + " to register for "+events_details.getEventName()+"\n"+ "Registration fee: \nFor Individual: Rs. " + events_details.getPrice();

                    Log.i("dialog",data);
                    break;

            case 2: data = "Are you sure " + name + " to register for "+events_details.getEventName()+"\n"+ "Registration fee: \nFor team: Rs." + events_details.getPrice_team();
                Log.i("dialog",data);
                    break;
            default: Snackbar.make(layout,"Poor internet connection, please try after sometime",Snackbar.LENGTH_SHORT).show(); return;
        }

//        if (Integer.parseInt(events_details.getPrice()) != 0 && Integer.parseInt(events_details.getType())==1 && participationType == 0){
//            data = "Are you sure " + name + " to register for "+events_details.getEventName()+"\n"+ "Registration fee: \nFor Individual: Rs. " + events_details.getPrice();
//        }else if (Integer.parseInt(events_details.getPrice()) != 0 && participationType==0 && participationType == 0){
//            data = "Are you sure " + name + " to register for "+events_details.getEventName()+"\n"+ "Registration fee: \nFor Individual: Rs. " + events_details.getPrice();
//        }else if (Integer.parseInt(events_details.getPrice()) != 0 && participationType==1 && participationType == 1){
//            data = "Are you sure " + name + " to register for "+events_details.getEventName()+"\n"+ "Registration fee: \nFor team: Rs." + events_details.getPrice_team();
//        }else{
//            data = "Are you sure " + name + " to register for "+events_details.getEventName()+"\n"+ "Registration fee: \nFor team: Rs." + events_details.getPrice_team();
//        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Event_details_register.this);
        alertDialog
               .setIcon(R.mipmap.fresh_logo_round)
                .setTitle("Confirm registration")
                .setMessage(data)
                //.setMessage("Are you sure")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.show();
                        register(regToken,events_details.getEventName(),events_details.getEventId(),uid,name,phone,email,referrer);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alertDialog.setTitle("Confirm registration");
        alert.show();
    }
    
    private void register(String reg_token,String e_name, String e_id, String p_uid, String p_name,String p_phone,String p_email,String p_refer) {

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        String data= "",fee = "",teammates;

        int casee=0;
        if (participationType%2==0){
            casee=2;
        }else{
            casee=1;
        }

        switch (casee){
            case 1: data = "\nEvent organised by: " + events_details.getOrganisingDept()+"\n" +
                            "Event id: " + "PROM-"+events_details.getEventId()+"\n"+
                            "Registered on: " + formattedDate + "\n"+
                            "Registration fee: \nFor Individual: " + events_details.getPrice();
                    fee = String.valueOf(Float.parseFloat(events_details.getPrice()));
                    break;

            case 2: data = "\nEvent organised by: " + events_details.getOrganisingDept()+"\n" +
                    "Event id: " + "PROM-"+events_details.getEventId()+"\n"+
                    "Registered on: " + formattedDate + "\n"+
                    "Registration fee: \nFor Team: " + events_details.getPrice_team() +"\nTeammates: " +teammatesNames.getText();
                    fee = String.valueOf(Float.parseFloat(events_details.getPrice_team()));
                    break;
        }


        String message = "Hello " + p_name + ",\nyour registration for our event at Promethean 2k18 was successful. " +
                "Looking forward to see you at the venue. Have a nice day!"+"\n"+
                "\n\nYour details\n\n"+"Name: " +p_name + "\n" + data;

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Urls.eventregister)
                .addBodyParameter("token",reg_token)
                .addBodyParameter("e_name",e_name)
                .addBodyParameter("e_id",e_id)
                .addBodyParameter("p_uid",p_uid)
                .addBodyParameter("p_name",p_name)
                .addBodyParameter("p_phone",p_phone)
                .addBodyParameter("p_email",p_email)
                .addBodyParameter("p_refer",p_refer)
                .addBodyParameter("data",data)
                .addBodyParameter("message",message)
                .addBodyParameter("url",events_details.getImageUrl())
                .addBodyParameter("type",events_details.getType())
                .addBodyParameter("e_email",events_details.getE_email())
                .addBodyParameter("teammates",teammatesNames.getText().toString())
                .addBodyParameter("indi_price",events_details.getPrice())
                .addBodyParameter("team_price",events_details.getPrice_team())
                .addBodyParameter("fee",fee)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ONRESPONSE",response);
                        if (response.trim().equals("exists")) {
                            Snackbar.make(layout, "You have already registered at the event, Thank You!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }else if(response.trim().equals("fresh")) {
                            Snackbar.make(layout, "Registered successfully, Thank You!", Toast.LENGTH_SHORT).setAction("Pay Fee", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Snackbar.make(layout,"Payments feature will be available soon, Sorry for the inconvenience",Snackbar.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Registered_Events.class));
                                    finish();
                                }
                            }).show();
                            radioGroup.clearCheck();
                            teammatesNames.setText("");
                            refer.setText("");
                            progressDialog.dismiss();
                        }else{
                            Snackbar.make(layout, response, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d("ONRESPONSE",anError.getMessage());
                        Snackbar.make(layout, "Something is wrong, please try after sometime", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void updateParts(){
        int noOfParticipants = Integer.parseInt(events_details.getTags());
        noOfParticipants++;
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.updateParticipationData)
                .addBodyParameter("e_id",events_details.getEventId())
                .addBodyParameter("e_part",String.valueOf(noOfParticipants))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ONRESPONSE",response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d("ONRESPONSE",anError.getMessage());
                       // Toast.makeText(Event_details_register.this, "Something is wrong, please try again later", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }
}
