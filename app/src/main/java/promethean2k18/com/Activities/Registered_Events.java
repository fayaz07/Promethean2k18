package promethean2k18.com.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import promethean2k18.com.Data_models.Register_model;
import promethean2k18.com.R;
import promethean2k18.com.Utils.InternetCheck;
import promethean2k18.com.Utils.Urls;

public class Registered_Events extends AppCompatActivity {

    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Register_model> reg_model;
    InternetCheck internetCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered__events);

        internetCheck = new InternetCheck(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Registered Events");

        reg_model = new ArrayList<>();
        reg_model.clear();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        recyclerView = findViewById(R.id.recyclerPerson);
        swipeRefreshLayout = findViewById(R.id.swiperefreshPerson);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.red),getResources().getColor(R.color.green));
        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        loadData();
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


    public void goToEventRegisterActivity(String regtoken){
        Intent intent = new Intent(getApplicationContext(), MyEventRegnStatus.class);
        intent.putExtra("token",regtoken);
        Log.i("regtoken",regtoken);
        startActivity(intent);
    }

    private void loadData(){
        if (!internetCheck.isIsInternetAvailable()){
            progressDialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(swipeRefreshLayout,"No internet connnection",Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            }).show();
            return;
        }
        progressDialog.show();
        swipeRefreshLayout.setRefreshing(true);
        Log.d("tag","loading started");
        reg_model.clear();
        progressDialog.show();
        swipeRefreshLayout.setRefreshing(true);
        AndroidNetworking.initialize(this);
        Log.d("tag","initialized fan");
        AndroidNetworking.post(Urls.retrieveregevents)
                .addBodyParameter("uid", FirebaseAuth.getInstance().getCurrentUser().getUid())
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
                            JSONArray jsonArray = jsonObject.getJSONArray("registrations");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Snackbar.make(swipeRefreshLayout, "You have not registered in any events", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                }).show();
                                return;
                            }
                            while (count < jsonArray.length()) {
                                Log.d("tag","events array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(count);
                                Register_model register_model = new Register_model();
                                register_model.setReg_token(j.getString("reg_token"));
                                register_model.setEvent_name(j.getString("event_name"));
//                                register_model.setEvent_id(j.getString("event_id"));
//                                register_model.setPart_uid(j.getString("part_uid"));
//                                register_model.setPart_name(j.getString("part_name"));
//                                register_model.setPart_phone(j.getString("part_phone"));
//                                register_model.setPart_email(j.getString("part_email"));
//                                register_model.setPart_referrer(j.getString("part_referer"));
                                register_model.setEvent_imageUrl(j.getString("url"));
                                register_model.setParticipationStatus(j.getString("part_status"));
//                                register_model.setData(j.getString("data"));
//                                register_model.setComments(j.getString("comments"));
                                register_model.setPaymentStatus(j.getString("payment_status"));
//                                register_model.setOrganizerEmail(j.getString("org_email"));
//                                register_model.setEventType(j.getString("e_type"));
//                                register_model.setTeammates(j.getString("team"));
//                                register_model.setEventPriceIndividual(j.getString("e_price_indi"));
//                                register_model.setEventPriceTeam(j.getString("e_price_team"));


                                reg_model.add(register_model);
                                count++;
                            }

                            RegisteredEventsAdapter adapter = new RegisteredEventsAdapter(getApplicationContext(),reg_model);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);

                        }catch (JSONException e){
                            Log.d("tag","exception " + e.getMessage());
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                            Snackbar.make(swipeRefreshLayout,"Something's gone wrong, please try after sometime",Toast.LENGTH_SHORT).show();
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
                        Snackbar.make(swipeRefreshLayout,"Something's gone wrong, please try after sometime",Toast.LENGTH_SHORT).show();
                    }
                });
    }
























    public class RegisteredEventsAdapter extends RecyclerView.Adapter<RegisteredEventsAdapter.RegistrationsView> {

        Context context;
        ArrayList<Register_model> register_models;

        public RegisteredEventsAdapter(Context context, ArrayList<Register_model> events_list) {
            this.context = context;
            this.register_models = events_list;
        }

        @NonNull
        @Override
        public RegistrationsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_regns,parent,false);
            RegistrationsView cv = new RegistrationsView(view);
            return cv;
        }

        @Override
        public void onBindViewHolder(@NonNull final RegistrationsView holder, final int position) {

            holder.eventName.setText(register_models.get(position).getEvent_name());
            holder.regToken.setText(register_models.get(position).getReg_token());
            Picasso.get().load(register_models.get(position).getEvent_imageUrl())
                    .fit()
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_event_available_black_24dp));
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
            holder.dept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToEventRegisterActivity(register_models.get(position).getReg_token());
                }
            });
            holder.paymentStatus.setText(register_models.get(position).getPaymentStatus());
        }

        @Override
        public int getItemCount() {
            return register_models.size();
        }

        class RegistrationsView extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView eventName, regToken,paymentStatus;
            CardView dept;
            ProgressBar progressBar;

            public RegistrationsView(View itemView) {
                super(itemView);
                paymentStatus = (TextView)itemView.findViewById(R.id.paymentStatus);
                imageView = (ImageView)itemView.findViewById(R.id.imageViewEventMyReg);
                dept = (CardView)itemView.findViewById(R.id.eventViewMyReg);
                eventName = (TextView)itemView.findViewById(R.id.eventNameMyReg);
                regToken = (TextView)itemView.findViewById(R.id.regToken);
                progressBar = (ProgressBar)itemView.findViewById(R.id.imageProgressEventMyReg);
            }
        }
    }

}
