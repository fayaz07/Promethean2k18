package promethean2k18.com.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import promethean2k18.com.Data_models.Events_model;
import promethean2k18.com.Login_signup.CompleteProfile;
import promethean2k18.com.Login_signup.Profile;
import promethean2k18.com.R;
import promethean2k18.com.Utils.InternetCheck;
import promethean2k18.com.Utils.Urls;

public class Home_final extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ArrayList<Events_model> events_models;
    ArrayList<Events_model> non_technical;

    RecyclerView deptartmental_events,fun_events;
    TextView show_no_internet_events_by_dept,show_no_internet_events_fun;
    ImageView hackthon,ideation,poster,project,faculty_co,student_co,team_prome,tech_team,notifications,college_map,sponsors,report_bug,council;
    LoadToast loadToast;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    int height,width,modifiedwidth,featuredSize,normalSize;
    int position = 0,max;
    ScrollView layout;
    InternetCheck internetCheck;
    View view;
    ProgressBar progressBar,nontechnicaleventsprogressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_final);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fun_events = findViewById(R.id.recyclerView_events_fun);

        loadToast = new LoadToast(this);
        loadToast.setTextColor(Color.BLACK).setBackgroundColor(Color.WHITE).setProgressColor(Color.BLUE).setBorderColor(Color.GRAY);
        loadToast.setText("Loading...");

        layout = findViewById(R.id.parentView_Home_new);

        progressBar = findViewById(R.id.loadingProgressBarDepartmentalEvents);
        progressBar.setIndeterminate(true);

        nontechnicaleventsprogressbar = findViewById(R.id.loadingProgressBarFunEvents);
        nontechnicaleventsprogressbar.setIndeterminate(true);

        show_no_internet_events_fun = findViewById(R.id.show_no_internet_events_fun);

        internetCheck = new InternetCheck(this);
//        if (!internetCheck.isIsInternetAvailable()){
//            Snackbar.make(layout,"No internet connection",Snackbar.LENGTH_LONG).show();
//        }
        non_technical = new ArrayList<>();


        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        loadToast.setTranslationY(height/2);
        //loadToast.show();

        modifiedwidth = width-28;
        featuredSize = modifiedwidth/2-100;
        normalSize = modifiedwidth/3-90;

        hackthon = findViewById(R.id.hacathon_n);
        hackthon.getLayoutParams().height = featuredSize;
        hackthon.getLayoutParams().width = featuredSize;
        hackthon.requestLayout();
        hackthon.setVisibility(View.VISIBLE);


        ideation = findViewById(R.id.ideation_n);
        ideation.getLayoutParams().height = featuredSize-10;
        ideation.getLayoutParams().width = featuredSize;
        ideation.requestLayout();
        ideation.setVisibility(View.VISIBLE);

        poster =findViewById(R.id.poster_presentation_n);
        poster.getLayoutParams().height = featuredSize;
        poster.getLayoutParams().width = featuredSize;
        poster.requestLayout();
        poster.setVisibility(View.VISIBLE);

        project = findViewById(R.id.project_expo_n);
        project.getLayoutParams().height = featuredSize;
        project.getLayoutParams().width = featuredSize;
        project.requestLayout();
        project.setVisibility(View.VISIBLE);

        faculty_co = findViewById(R.id.faculty_coordinators_n);
        faculty_co.getLayoutParams().height = normalSize;
        faculty_co.getLayoutParams().width = normalSize;
        faculty_co.requestLayout();
        faculty_co.setVisibility(View.VISIBLE);

        student_co = findViewById(R.id.student_coordinators_n);
        student_co.getLayoutParams().height = normalSize;
        student_co.getLayoutParams().width = normalSize;
        student_co.requestLayout();
        student_co.setVisibility(View.VISIBLE);

        team_prome = findViewById(R.id.team_promethean_n);
        team_prome.getLayoutParams().height = normalSize;
        team_prome.getLayoutParams().width = normalSize;
        team_prome.requestLayout();
        team_prome.setVisibility(View.VISIBLE);

        sponsors = findViewById(R.id.sponsors_n);
        sponsors.getLayoutParams().height = normalSize;
        sponsors.getLayoutParams().width = normalSize;
        sponsors.requestLayout();
        sponsors.setVisibility(View.VISIBLE);

        notifications = findViewById(R.id.notifications_n);
        notifications.getLayoutParams().height = normalSize;
        notifications.getLayoutParams().width = normalSize;
        notifications.requestLayout();
        notifications.setVisibility(View.VISIBLE);

        report_bug = findViewById(R.id.report_bug_n);
        report_bug.getLayoutParams().height = normalSize;
        report_bug.getLayoutParams().width = normalSize;
        report_bug.requestLayout();
        report_bug.setVisibility(View.VISIBLE);

        college_map = findViewById(R.id.college_map_n);
        college_map.getLayoutParams().height = normalSize;
        college_map.getLayoutParams().width = normalSize;
        college_map.requestLayout();
        college_map.setVisibility(View.VISIBLE);

        tech_team = findViewById(R.id.about_techteam_n);
        tech_team.getLayoutParams().height = normalSize;
        tech_team.getLayoutParams().width = normalSize;
        tech_team.requestLayout();
        tech_team.setVisibility(View.VISIBLE);

        council = findViewById(R.id.council);
        council.getLayoutParams().height = normalSize;
        council.getLayoutParams().width = normalSize;
        council.requestLayout();
        council.setVisibility(View.VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Promethean 2k18");

        events_models = new ArrayList<>();
        events_models.clear();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        deptartmental_events = findViewById(R.id.recyclerView_events_by_departments);
        show_no_internet_events_by_dept = findViewById(R.id.show_no_internet_events_by_departments);
        show_no_internet_events_by_dept.setVisibility(View.GONE);


        loadData();

        //Listeners
        hackthon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Event_details_register.class);
                intent.putExtra("id","1001");
                intent.putExtra("type","f");
                startActivity(intent);
            }
        });
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Event_details_register.class);
                intent.putExtra("id","2001");
                intent.putExtra("type","f");
                startActivity(intent);
            }
        });
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Event_details_register.class);
                intent.putExtra("id","3001");
                intent.putExtra("type","f");
                startActivity(intent);
            }
        });
        ideation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Event_details_register.class);
                intent.putExtra("id","4001");
                intent.putExtra("type","f");
                startActivity(intent);
            }
        });
        team_prome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Team_promethean.class));
            }
        });
        student_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Student_co_ordinators.class));
            }
        });
        faculty_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Faculty_coordinators.class);
                intent.putExtra("type","fac");
                startActivity(intent);
            }
        });
        college_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 17.7252584, 78.2571511);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Notifications.class));
            }
        });
        sponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Sponsors.class));
            }
        });
        tech_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),OpenSourceLicenses_TechTeam.class));
            }
        });
        report_bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Report_Bug.class));
            }
        });

        council.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Faculty_coordinators.class);
                intent.putExtra("type","coun");
                startActivity(intent);
            }
        });
        show_no_internet_events_fun.setVisibility(View.GONE);
        activity_close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_final, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.bringToFront();
        drawer.requestLayout();

        if (id == R.id.show_my_profile){
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.post(Urls.checknew)
                    .addBodyParameter("uid",FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ONRESPONSE",response);
                            if(response.trim().equals("new")) {
                                Snackbar.make(layout, "We don't have your data, please fill it!", Snackbar.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),CompleteProfile.class));
                            }else {
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                            }
                        }
                        @Override
                        public void onError(ANError anError) {
                            Log.d("ONRESPONSE",anError.toString());
                            Snackbar.make(layout, "Please try after some time", Snackbar.LENGTH_LONG).show();
                        }
                    });
        }
        if (id == R.id.show_my_registered_eents){
            startActivity(new Intent(getApplicationContext(), Registered_Events.class));
        }
        if (id==R.id.log_me_out){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "You have been successfully logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        if (id==R.id.share_app){

            final String appPackageName = getPackageName(); // package name of the app
//            try {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//            } catch (android.content.ActivityNotFoundException anfe) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//            }

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out this Technical fest's android app, Promethean 2k18 by B V Raju Institute of Technology, Narsapur, Telangana at: https://play.google.com/store/apps/details?id="+appPackageName);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if (id==R.id.communicate_with_us){
            startActivity(new Intent(getApplicationContext(),Communicate_with_us.class));
        }
        if (id==R.id.college_buses){
            startActivity(new Intent(getApplicationContext(),Routes_View.class));
        }
        if (id==R.id.how_it_works){
            startActivity(new Intent(getApplicationContext(),How_it_works.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void activity_close(){
        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            Slide slide = new Slide();
            slide.setDuration(2000);
            getWindow().setExitTransition(slide);

            Fade fade = new Fade();
            fade.setDuration(2000);
            getWindow().setEnterTransition(fade);

            getWindow().setReturnTransition(slide);

            getWindow().setReenterTransition(fade);

        } else {
            // Swap without transition
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.open_about_promethean){
            startActivity(new Intent(getApplicationContext(),About_promethean.class));
        }
        if (view.getId()== R.id.open_all_events){
            Intent intent = new Intent(getApplicationContext(), Events_Workshops.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.open_all_fun_events){
            Snackbar.make(layout, "We will update the information soon, Thanks for your interest", Snackbar.LENGTH_SHORT).show();
        }
//        if (view.getId()==R.id.right_arrow){
//            //Toast.makeText(this, "We will update the information soon, Thanks for your interest", Toast.LENGTH_SHORT).show();
//            deptartmental_events.scrollToPosition( (++position)%max);
//        }
//        if (view.getId()==R.id.left_arrow){
//            //Toast.makeText(this, "We will update the information soon, Thanks for your interest", Toast.LENGTH_SHORT).show();
//            deptartmental_events.scrollToPosition( (--position)%max);
//        }
    }

    //Goes to events detailed page
    private void goToEventRegisterActivity(String eventId){
        Intent intent = new Intent(getApplicationContext(), Event_details_register.class);
        intent.putExtra("id",eventId);
        intent.putExtra("type","n");
        startActivity(intent);
    }

    //Goes to non-technical events detailed page
    private void goToEventRegisterActivityNon(String eventId){
        Intent intent = new Intent(getApplicationContext(), Event_details_register.class);
        intent.putExtra("id",eventId);
        intent.putExtra("type","non");
        startActivity(intent);
    }

    private void loadData(){

        if (!internetCheck.isIsInternetAvailable()){
            show_no_internet_events_by_dept.setText("No internet connection");
            show_no_internet_events_by_dept.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            loadToast.hide();
            Snackbar.make(layout,"No internet connection",Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            }).show();
            return;
        }
        loadToast.show();
        show_no_internet_events_by_dept.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        events_models.clear();

        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.retrieveeventsdata)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("events");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Snackbar.make(layout, "No events are available  currently", Snackbar.LENGTH_SHORT).show();
                            }
                            max = 10;
                            while (count < 10) {
                                Log.d("tag","events array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(count);
                                Events_model events_model = new Events_model();
                                events_model.setEventId(j.getString("id").trim());
                                events_model.setEventName(j.getString("name").trim());
                                events_model.setTags(j.getString("participants").trim());
                                events_model.setImageUrl(j.getString("url").trim());
                                events_model.setType(String.valueOf(j.getString("type")).trim());
                                events_model.setE_email(j.getString("e_email").trim());
                                events_models.add(events_model);
                                count++;
                                Log.i("ev_count", String.valueOf(count));
                            }

                            Events_adapter_f adapter = new Events_adapter_f(getApplicationContext(),events_models);
                            deptartmental_events.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                            deptartmental_events.setHasFixedSize(true);
                            deptartmental_events.setAdapter(adapter);

                        }catch (JSONException e) {
                            Log.d("tag", "exception " + e.getMessage());
                            e.printStackTrace();
                            Snackbar.make(layout, "Sorry, we are unable to retrieve departmental events currrently, check back soon",  Snackbar.LENGTH_SHORT).show();
                            show_no_internet_events_by_dept.setText("Can't retrieve data, please check back soon");
                            show_no_internet_events_by_dept.setVisibility(View.VISIBLE);
                            show_no_internet_events_by_dept.setGravity(Gravity.CENTER);
                            show_no_internet_events_by_dept.requestLayout();
                        }finally {
                            show_no_internet_events_by_dept.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag","anerror " +anError.getResponse()+anError.getErrorBody()+anError.getMessage());
                        Snackbar.make(layout,"Check your internet, avoid proxied wifi",Snackbar.LENGTH_SHORT).show();
                        show_no_internet_events_by_dept.setText("Can't retrieve data, please check back soon");
                        show_no_internet_events_by_dept.setVisibility(View.VISIBLE);
                        show_no_internet_events_by_dept.setGravity(Gravity.CENTER);
                        show_no_internet_events_by_dept.requestLayout();
                        progressBar.setVisibility(View.GONE);
                    }
                });
        loadDataNon();
        AndroidNetworking.post(Urls.checkIfUpdateAvailable)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ONRESPONSE",response);
                        if(!response.trim().isEmpty()) {
                            Snackbar.make(layout, response+"!", Snackbar.LENGTH_LONG).setAction("UPDATE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final String appPackageName = getPackageName(); // package name of the app
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            }).show();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });
        loadToast.success();
    }

    private void loadDataNon(){
        loadToast.show();
        show_no_internet_events_by_dept.setVisibility(View.INVISIBLE);
        nontechnicaleventsprogressbar.setVisibility(View.VISIBLE);
        non_technical.clear();
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.getNonTechEvents)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("events");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Snackbar.make(layout, "No non-technical events are available currently", Snackbar.LENGTH_SHORT).show();
                            }
                            while (count < jsonArray.length()) {
                                Log.d("tag","non technical array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(count);
                                Events_model events_model = new Events_model();
                                events_model.setEventId(j.getString("id").trim());
                                events_model.setEventName(j.getString("name").trim());
                                events_model.setTags(j.getString("participants").trim());
                                events_model.setImageUrl(j.getString("url").trim());
                                events_model.setType(String.valueOf(j.getString("type")).trim());
                                events_model.setE_email(j.getString("e_email").trim());
                                non_technical.add(events_model);
                                count++;
                                Log.i("ev_count", String.valueOf(count));
                            }
                            Events_adapter_fn adapter = new Events_adapter_fn(getApplicationContext(),non_technical);
                            fun_events.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                            fun_events.setHasFixedSize(true);
                            fun_events.setAdapter(adapter);
                            show_no_internet_events_fun.setVisibility(View.GONE);
                            nontechnicaleventsprogressbar.setVisibility(View.GONE);
                        }catch (JSONException e) {
                            Log.d("tag", "exception " + e.getMessage());
                            e.printStackTrace();
                            Snackbar.make(layout, "Sorry, we are unable to retrieve non-technical events currrently, check back soon",  Snackbar.LENGTH_SHORT).show();
                            show_no_internet_events_fun.setText("Can't retrieve data, please check back soon");
                            show_no_internet_events_fun.setVisibility(View.VISIBLE);
                            nontechnicaleventsprogressbar.setVisibility(View.GONE);
                            show_no_internet_events_fun.setGravity(Gravity.CENTER);
                            show_no_internet_events_fun.requestLayout();
                        }finally {
                            show_no_internet_events_fun.setGravity(Gravity.CENTER);
                            show_no_internet_events_fun.requestLayout();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag","anerror " +anError.getResponse()+anError.getErrorBody()+anError.getMessage());
                        Snackbar.make(layout,"Check your internet, avoid proxied wifi",Snackbar.LENGTH_SHORT).show();
                        show_no_internet_events_fun.setText("Can't retrieve data, please check back soon");
                        show_no_internet_events_fun.setVisibility(View.VISIBLE);
                        nontechnicaleventsprogressbar.setVisibility(View.GONE);
                        show_no_internet_events_fun.setGravity(Gravity.CENTER);
                        show_no_internet_events_fun.requestLayout();
                    }
                });

    }

    class Events_adapter_f extends RecyclerView.Adapter<Events_adapter_f.Events_view> {

        Context context;
        ArrayList<Events_model> events_list;

        public Events_adapter_f(Context context, ArrayList<Events_model> events_list) {
            this.context = context;
            this.events_list = events_list;
        }

        @NonNull
        @Override
        public Events_adapter_f.Events_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_layout,parent,false);
            Events_adapter_f.Events_view cv = new Events_adapter_f.Events_view(view);
            return cv;
        }

        @Override
        public void onBindViewHolder(@NonNull final Events_adapter_f.Events_view holder, final int position) {

            holder.eventName.setText(events_list.get(position).getEventName());
            holder.noOfParticipants.setText("Tags: " + events_list.get(position).getTags());
            Picasso.get().load(events_list.get(position).getImageUrl())
                    .fit()
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_event_available_black_24dp));
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
            holder.dept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToEventRegisterActivity(events_list.get(position).getEventId());
                    Log.i("event_id",events_list.get(position).getEventId());
                }
            });

        }

        @Override
        public int getItemCount() {
            return events_list.size();
        }

        class Events_view extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView eventName, noOfParticipants;
            CardView dept;
            ProgressBar progressBar;

            public Events_view(View itemView) {
                super(itemView);
                imageView = (ImageView)itemView.findViewById(R.id.imageViewTeam);
                dept = (CardView)itemView.findViewById(R.id.eventView);
                eventName = (TextView)itemView.findViewById(R.id.eventName);
                noOfParticipants = (TextView)itemView.findViewById(R.id.tags);
                progressBar = (ProgressBar)itemView.findViewById(R.id.imageProgressEvent);
            }
        }
    }

    class Events_adapter_fn extends RecyclerView.Adapter<Events_adapter_fn.Events_view> {

        Context context;
        ArrayList<Events_model> events_list;

        public Events_adapter_fn(Context context, ArrayList<Events_model> events_list) {
            this.context = context;
            this.events_list = events_list;
        }

        @NonNull
        @Override
        public Events_adapter_fn.Events_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_layout,parent,false);
            Events_adapter_fn.Events_view cv = new Events_adapter_fn.Events_view(view);
            return cv;
        }

        @Override
        public void onBindViewHolder(@NonNull final Events_adapter_fn.Events_view holder, final int position) {

            holder.eventName.setText(events_list.get(position).getEventName());
            holder.noOfParticipants.setText("Tags: " + events_list.get(position).getTags());
            Picasso.get().load(events_list.get(position).getImageUrl())
                    .fit()
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_event_available_black_24dp));
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
            holder.dept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToEventRegisterActivityNon(events_list.get(position).getEventId());
                    Log.i("event_id",events_list.get(position).getEventId());
                }
            });

        }

        @Override
        public int getItemCount() {
            return events_list.size();
        }

        class Events_view extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView eventName, noOfParticipants;
            CardView dept;
            ProgressBar progressBar;

            public Events_view(View itemView) {
                super(itemView);
                imageView = (ImageView)itemView.findViewById(R.id.imageViewTeam);
                dept = (CardView)itemView.findViewById(R.id.eventView);
                eventName = (TextView)itemView.findViewById(R.id.eventName);
                noOfParticipants = (TextView)itemView.findViewById(R.id.tags);
                progressBar = (ProgressBar)itemView.findViewById(R.id.imageProgressEvent);
            }
        }
    }
}
