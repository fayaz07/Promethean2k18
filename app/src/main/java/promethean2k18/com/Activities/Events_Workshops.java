package promethean2k18.com.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import promethean2k18.com.Data_models.Events_model;
import promethean2k18.com.R;
import promethean2k18.com.Utils.InternetCheck;
import promethean2k18.com.Utils.Urls;

public class Events_Workshops extends AppCompatActivity {

    private ProgressDialog progressDialog;
    Intent intent;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Events_model> events_models;

    InternetCheck internetCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_by_dept);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        events_models = new ArrayList<>();
        events_models.clear();

        internetCheck = new InternetCheck(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerEvents);
        swipeRefreshLayout = findViewById(R.id.swiperefreshEvent);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.red),getResources().getColor(R.color.green));

        loadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.departments_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.cse_events_list){
            loadDataAccordingToDepartments("Computer Science and Engineering");
            return true;
        }
        if (item.getItemId() == R.id.ece_events_list){
            loadDataAccordingToDepartments("Electronics And Communication Engineering");
            return true;
        }
        if (item.getItemId() == R.id.it_events_list){
            loadDataAccordingToDepartments("Information Technology");
            return true;
        }
        if (item.getItemId() == R.id.che_events_list){
            loadDataAccordingToDepartments("Chemical Engineering");
            return true;
        }
        if (item.getItemId() == R.id.civ_events_list){
            loadDataAccordingToDepartments("Civil Engineering");
            return true;
        }
        if (item.getItemId() == R.id.mec_events_list){
            loadDataAccordingToDepartments("Mechanical Engineering");
            return true;
        }
        if (item.getItemId() == R.id.bme_events_list){
            loadDataAccordingToDepartments("Bio Medical Engineering");
            return true;
        }
        if (item.getItemId() == R.id.phe_events_list){
            loadDataAccordingToDepartments("Pharmaceutical Engineering");
            return true;
        }
        if (item.getItemId() == R.id.mba_events_list){
            loadDataAccordingToDepartments("Master of Business Administration");
            return true;
        }
        if (item.getItemId() == R.id.eee_events_list){
            loadDataAccordingToDepartments("Electrical and Electronics Engineering");
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void goToEventRegisterActivity(String eventId){
        Intent intent = new Intent(getApplicationContext(), Event_details_register.class);
        intent.putExtra("id",eventId);
        intent.putExtra("type","n");
        startActivity(intent);
    }

    private void loadData(){
        if(!internetCheck.isIsInternetAvailable()){
            Snackbar.make(swipeRefreshLayout,"No internet connection",Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            }).show();
            return;
        }
        Log.d("tag","loading started");
        events_models.clear();
        progressDialog.show();
        swipeRefreshLayout.setRefreshing(true);
        AndroidNetworking.initialize(this);
        Log.d("tag","initialized fan");

        String urll = "";

        urll = Urls.retrieveeventsdata;

        AndroidNetworking.post(urll)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("tag","onresponse of fan");
                        progressDialog.show();
                        try {

                            JSONObject jsonObject = new JSONObject();
                            jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("events");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Snackbar.make(swipeRefreshLayout, "Currently we don't have any events shceduled", Snackbar.LENGTH_LONG).show();
                            }
                            while (count < jsonArray.length()) {
                                Log.d("tag","events array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(count);
                                Events_model events_model = new Events_model();
                                events_model.setEventId(j.getString("id"));
                                events_model.setEventName(j.getString("name"));
                                events_model.setTags(j.getString("participants"));
                                events_model.setImageUrl(j.getString("url"));
                                events_model.setType(j.getString("type"));
                                events_models.add(events_model);
                                count++;
                            }
                            Events_adapter adapter = new Events_adapter(getApplicationContext(),events_models);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }catch (JSONException e){
                            Log.d("tag","exception " + e.getMessage());
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                            Snackbar.make(swipeRefreshLayout,"Something's gone wrong, please try again",Toast.LENGTH_SHORT).show();
                        }finally {
                            Log.d("tag","setting adapter");
                            progressDialog.dismiss();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag","anerror " +anError.getResponse()+anError.getErrorBody());
                        progressDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(swipeRefreshLayout,"Check your internet, avoid proxied wifi",Toast.LENGTH_LONG).show();
                    }
                });
        swipeRefreshLayout.setRefreshing(false);
    }


    private void loadDataAccordingToDepartments(final String deptName){
        if(!internetCheck.isIsInternetAvailable()){
            Snackbar.make(swipeRefreshLayout,"No internet connection",Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            }).show();
            return;
        }
        events_models.clear();
        progressDialog.show();
        swipeRefreshLayout.setRefreshing(true);
        AndroidNetworking.initialize(this);

        String urll = "";
        urll = Urls.getEventsByDepartment;

        AndroidNetworking.post(urll)
                .addBodyParameter("dept",deptName)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.show();
                        try {

                            JSONObject jsonObject = new JSONObject();
                            jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("events");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Snackbar.make(swipeRefreshLayout, "Currently we don't have any events shceduled by " + deptName, Snackbar.LENGTH_LONG).show();
                            }
                            while (count < jsonArray.length()) {
                                Log.d("tag","events array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(count);
                                Events_model events_model = new Events_model();
                                events_model.setEventId(j.getString("id"));
                                events_model.setEventName(j.getString("name"));
                                events_model.setTags(j.getString("participants"));
                                events_model.setImageUrl(j.getString("url"));
                                events_model.setType(j.getString("type"));
                                events_models.add(events_model);
                                count++;
                            }
                            Events_adapter adapter = new Events_adapter(getApplicationContext(),events_models);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }catch (JSONException e){
                            Log.d("tag","exception " + e.getMessage());
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                            Snackbar.make(swipeRefreshLayout,"Something's gone wrong, please try again",Toast.LENGTH_SHORT).show();
                        }finally {
                            Log.d("tag","setting adapter");
                            progressDialog.dismiss();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag","anerror " +anError.getResponse()+anError.getErrorBody());
                        progressDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(swipeRefreshLayout,"Check your internet, avoid proxied wifi",Toast.LENGTH_LONG).show();
                    }
                });
        swipeRefreshLayout.setRefreshing(false);
    }





















    //Adapter
    public class Events_adapter extends RecyclerView.Adapter<Events_adapter.Events_view> {

        Context context;
        ArrayList<Events_model> events_list;

        public Events_adapter(Context context, ArrayList<Events_model> events_list) {
            this.context = context;
            this.events_list = events_list;
        }

        @NonNull
        @Override
        public Events_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_layout,parent,false);
            Events_adapter.Events_view cv = new Events_adapter.Events_view(view);
            return cv;
        }

        @Override
        public void onBindViewHolder(@NonNull final Events_view holder, final int position) {

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
