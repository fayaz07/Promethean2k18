package promethean2k18.com.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
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

import promethean2k18.com.Data_models.Notifications_Model;
import promethean2k18.com.R;
import promethean2k18.com.Utils.Urls;

public class Notifications extends AppCompatActivity {

    ArrayList<Notifications_Model> notifications_models;
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        notifications_models = new ArrayList<>();
        notifications_models.clear();
        recyclerView = findViewById(R.id.recyckerNoti);

        loadData();
    }

    private void loadData(){
        Log.d("dept","loading started");
        notifications_models.clear();
        progressDialog.show();
        AndroidNetworking.initialize(this);
        Log.d("dept","initialized fan");
        AndroidNetworking.post(Urls.retrievenotifications)
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
                            JSONArray jsonArray = jsonObject.getJSONArray("notifications");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Toast.makeText(getApplicationContext(), "There is no data", Toast.LENGTH_SHORT).show();
                            }
                            while (count < jsonArray.length()) {
                                Log.d("dept","json array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(count);

                                Notifications_Model n = new Notifications_Model();
                                n.setContent(j.getString("desc"));
                                n.setTag(j.getString("tag"));
                                n.setImageurl(j.getString("url"));
                                n.setTimestamp(j.getString("ts"));

                                notifications_models.add(n);
                                count++;
                            }

                        }catch (JSONException e){
                            Log.d("dept","exception " + e.getMessage());
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Something's gone wrong",Toast.LENGTH_SHORT).show();
                        }finally {
                            Log.d("dept","setting adapter");
                            Notifications_adapter adapter = new Notifications_adapter(getApplicationContext(),notifications_models);
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














    public class Notifications_adapter extends RecyclerView.Adapter<Notifications_adapter.Noti_view> {

        Context context;
        private ArrayList<Notifications_Model> depts_list;

        public Notifications_adapter(Context context, ArrayList<Notifications_Model> depts_list) {
            this.context = context;
            this.depts_list = depts_list;
        }

        @NonNull
        @Override
        public Notifications_adapter.Noti_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_layout,parent,false);
            Notifications_adapter.Noti_view cv = new Notifications_adapter.Noti_view(view);
            return cv;
        }


        @Override
        public void onBindViewHolder(@NonNull final Notifications_adapter.Noti_view holder, final int position) {
            //String url =  "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
            holder.progressBar.setIndeterminate(true);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.tag.setText(depts_list.get(position).getTag());
            holder.timestamp.setText(depts_list.get(position).getTimestamp());
            holder.desc.setText(depts_list.get(position).getContent());

            Picasso.get().load(depts_list.get(position).getImageurl())
                    .fit()
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

        class Noti_view extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView tag, timestamp,desc;
            ProgressBar progressBar;

            public Noti_view(View itemView) {
                super(itemView);
                imageView = (ImageView)itemView.findViewById(R.id.imageViewNoti);
                tag = itemView.findViewById(R.id.tagNoti);
                timestamp = (TextView)itemView.findViewById(R.id.timestramp);
                desc = (TextView)itemView.findViewById(R.id.descNoti);
                progressBar = (ProgressBar)itemView.findViewById(R.id.imageProgressNoti);
            }
        }
    }

}
