package promethean2k18.com.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import promethean2k18.com.Data_models.Sponsor_model;
import promethean2k18.com.R;
import promethean2k18.com.Utils.Urls;

public class Sponsors extends AppCompatActivity {

    private ProgressDialog progressDialog;
    ArrayList<Sponsor_model> sponsor_models;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sponsors");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        sponsor_models = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerSponsor);
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
       // startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
    }

    private void loadData(){
        Log.d("dept","loading started");
        sponsor_models.clear();
        progressDialog.show();

        AndroidNetworking.initialize(this);
        Log.d("dept","initialized fan");
        AndroidNetworking.post(Urls.retrievesponsor)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("dept","onresponse of fan");
                        progressDialog.show();
                        try {

                            JSONObject jsonObject = new JSONObject();
                            jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("sponsors");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Toast.makeText(getApplicationContext(), "There is no data", Toast.LENGTH_SHORT).show();
                            }
                            while (count < jsonArray.length()) {
                                Log.d("dept","json array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(count);
                                Sponsor_model sponsor = new Sponsor_model();
                                sponsor.setImage(j.getString("url"));
                                sponsor.setName(j.getString("name"));
                                sponsor_models.add(sponsor);
                                count++;
                            }

                        }catch (JSONException e){
                            Log.d("dept","exception " + e.getMessage());
                            e.printStackTrace();
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),"Something's gone wrong",Toast.LENGTH_SHORT).show();
                        }finally {
                            Log.d("dept","setting adapter");
                            Sponsors_adapter adapter = new Sponsors_adapter(getApplicationContext(),sponsor_models);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        // Log.d("error",""+anError);
                        Log.d("dept","anerror " +anError.getResponse()+anError.getErrorBody());
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Check your internet, avoid proxied wifi networks",Toast.LENGTH_SHORT).show();
                    }
                });
//        setAdapter();
    }







    //Adapter
    public class Sponsors_adapter extends RecyclerView.Adapter<Sponsors_adapter.Sponsors_view> {

        Context context;
        private ArrayList<Sponsor_model> depts_list;

        public Sponsors_adapter(Context context, ArrayList<Sponsor_model> depts_list) {
            this.context = context;
            this.depts_list = depts_list;
        }

        @NonNull
        @Override
        public Sponsors_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sponsors_layout,parent,false);
            Sponsors_view cv = new Sponsors_view(view);
            return cv;
        }


        @Override
        public void onBindViewHolder(@NonNull final Sponsors_adapter.Sponsors_view holder, final int position) {
            //String url =  "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
            holder.progressBar.setIndeterminate(true);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.spon.setText(depts_list.get(position).getName());

            Picasso.get().load(depts_list.get(position).getImage())
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_error_outline_black_24dp));
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });

        }

        @Override
        public int getItemCount() {
            return depts_list.size();
        }

        class Sponsors_view extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView spon;
            ProgressBar progressBar;

            public Sponsors_view(View itemView) {
                super(itemView);
                imageView = (ImageView)itemView.findViewById(R.id.imageViewSponsor);
                spon = (TextView) itemView.findViewById(R.id.sponsorName);
                progressBar = (ProgressBar)itemView.findViewById(R.id.imageProgressSponsor);
            }
        }
    }
}
