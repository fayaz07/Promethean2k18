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
import android.widget.BaseAdapter;
import android.widget.GridView;
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

import de.hdodenhof.circleimageview.CircleImageView;
import promethean2k18.com.R;
import promethean2k18.com.Data_models.Team_model;
import promethean2k18.com.Utils.Urls;

public class Student_co_ordinators extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<Team_model> team_models;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_co_ordinators);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Team Promethean");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        team_models = new ArrayList<>();
        team_models.clear();
        recyclerView = findViewById(R.id.recyclerStudents);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
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
      //  startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
    }

    private void loadData(){
        Log.d("dept","loading started");
        team_models.clear();
        progressDialog.show();
        AndroidNetworking.initialize(this);
        Log.d("dept","initialized fan");
        AndroidNetworking.post(Urls.team_co)
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
                            JSONArray jsonArray = jsonObject.getJSONArray("team");
                            int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Toast.makeText(getApplicationContext(), "There is no data", Toast.LENGTH_SHORT).show();
                            }
                            while (count < jsonArray.length()) {
                                Log.d("dept","json array " + count+" ");
                                JSONObject j = jsonArray.getJSONObject(count);

                                Team_model t = new Team_model();
                                t.setDept(j.getString("dept"));
                                t.setImage(j.getString("image"));
                                t.setName(j.getString("name"));
                                t.setContact(j.getString("contact"));
                                team_models.add(t);
                                count++;
                            }

                        }catch (JSONException e){
                            Log.d("dept","exception " + e.getMessage());
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Something's gone wrong",Toast.LENGTH_SHORT).show();
                        }finally {
                            Log.d("dept","setting adapter");
                            St_co_adapter adapter = new St_co_adapter(getApplicationContext(),team_models);

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
                        Toast.makeText(getApplicationContext(),"Something's gone wrong" + anError,Toast.LENGTH_SHORT).show();
                    }
                });
//        setAdapter();
    }




    public class St_co_adapter extends RecyclerView.Adapter<St_co_adapter.St_co_view> {

        Context context;
        private ArrayList<Team_model> depts_list;

        public St_co_adapter(Context context, ArrayList<Team_model> depts_list) {
            this.context = context;
            this.depts_list = depts_list;
        }

        @NonNull
        @Override
        public St_co_adapter.St_co_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_layout,parent,false);
            St_co_adapter.St_co_view cv = new St_co_adapter.St_co_view(view);
            return cv;
        }

        @Override
        public void onBindViewHolder(@NonNull final St_co_adapter.St_co_view holder, final int position) {
            //String url =  "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
            holder.progressBar.setIndeterminate(true);
            holder.progressBar.setVisibility(View.VISIBLE);

            holder.name.setText(depts_list.get(position).getName().trim());
            holder.dept.setText(depts_list.get(position).getDept().trim());
            holder.contact.setText(depts_list.get(position).getContact().trim());

            Picasso.get().load(depts_list.get(position).getImage())
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black_24dp));
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return depts_list.size();
        }

        class St_co_view extends RecyclerView.ViewHolder{

            CircleImageView imageView;
            TextView name,contact,dept;
            ProgressBar progressBar;

            public St_co_view(View itemView) {
                super(itemView);
                imageView = (CircleImageView) itemView.findViewById(R.id.imageViewTeam);
                dept = itemView.findViewById(R.id.deptTeam);
                name = itemView.findViewById(R.id.TeamName);
                contact = itemView.findViewById(R.id.contactTeam);
                progressBar = (ProgressBar)itemView.findViewById(R.id.imageProgressTeam);
            }
        }
    }


}
