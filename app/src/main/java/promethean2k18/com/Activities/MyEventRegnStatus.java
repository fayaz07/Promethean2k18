package promethean2k18.com.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.zxing.WriterException;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import promethean2k18.com.Data_models.Register_model;
import promethean2k18.com.R;
import promethean2k18.com.Utils.InternetCheck;
import promethean2k18.com.Utils.Urls;

public class MyEventRegnStatus extends Activity {

    ImageView imageView;
    ProgressDialog progressDialog;
    InternetCheck internetCheck;
    ScrollView layout;
    Button payFee;
    TextView e_name, reg_status, data, comments, payment_status, pariticipants,or_email,teammates,registrationfee;
    String token;
    Intent intent;
    Register_model register_model;
    LoadToast loadToast;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    private static final int TEZ_REQUEST_CODE = 123;
    Button sendEmail;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    private float feeToBePaid = 0.0f;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_regn_status);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        intent = getIntent();
        token = intent.getStringExtra("token");
        Log.i("regtokenr",token);

        register_model = new Register_model();

        sendEmail = findViewById(R.id.sendPaymentScreenshots);

        e_name = findViewById(R.id.event_name_regn_status);
        reg_status = findViewById(R.id.status_regn_status);
        data = findViewById(R.id.data_regn_status);
        comments = findViewById(R.id.regn_status_comments);
        payment_status = findViewById(R.id.payment_status_comments);
        pariticipants = findViewById(R.id.team_status);
        payFee = findViewById(R.id.pay_registration_fee);

        layout = findViewById(R.id.parentMyRegnStatus);
        or_email = findViewById(R.id.or_email_status);
        registrationfee = findViewById(R.id.registrationAmount);

        imageView = findViewById(R.id.show_my_reg_token_qr);
        internetCheck = new InternetCheck(this);

        loadData();

//        QRGEncoder encoder = new QRGEncoder("16211A05F7|2",null,QRGContents.Type.TEXT,512);
//        try {
//            Bitmap bitmap = encoder.encodeAsBitmap();
//            imageView.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();;
//        }

        TextView accName = new TextView(this);
        accName.setText("DR BVRIT PROMETHEAN");

        TextView accNo = new TextView(this);
        accNo.setText("62092749799");

        TextView ifsc = new TextView(this);
        ifsc.setText("SBIN0020105");

        TextView email = new TextView(this);
        email.setText("admin@promethean2k18.com");

        textView = new TextView(this);
        textView.setPadding(30,30,30,30);
        textView.setTypeface(Typeface.SANS_SERIF);

        textView.setTextIsSelectable(true);

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager manager =
                        (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                TextView showTextParam = (TextView) v;
                manager.setText( showTextParam.getText() );
                // Show a message:
                Toast.makeText(v.getContext(), "Text in clipboard",
                        Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
        });

        AlertDialog.Builder al = new AlertDialog.Builder(this)
                .setTitle("Please pay through NEFT")
                .setView(textView)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = al.create();

        payFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(layout,"We are working on it, please try later",Snackbar.LENGTH_SHORT).show();
                alertDialog.show();
            }
        });

        /*
        Change
Promethean Payments
paypromethean@gmail.com
         */
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL , new
                        String[]{"admin@promethean2k18.com","paypromethean@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Payment of Registration fee for " + register_model.getEvent_name());
                emailIntent.putExtra(Intent.EXTRA_TEXT
                        , "Hello admin,\n I am " + register_model.getPart_name() +"\n\nUID: + " + register_model.getPart_uid() +"\nRegistration Token: " + register_model.getReg_token()
                                +"\nRegistration Data: \n"+register_model.getData()+"\nContact details: " + register_model.getPart_email()+", "
                                + register_model.getPart_phone()
                                + "\n\nPlease consider my registration and update the status, I have uploaded the screenshots as attachments");
                Toast.makeText(MyEventRegnStatus.this, "Please attatch payment screenshots, while sending email", Toast.LENGTH_LONG).show();
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
    }

    private void loadData(){
        if (!internetCheck.isIsInternetAvailable()){
            progressDialog.dismiss();

            Snackbar.make(layout,"No internet connnection",Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            }).show();
            return;
        }
        progressDialog.show();

        Log.d("tag","loading started");
        progressDialog.show();
        AndroidNetworking.initialize(this);
        Log.d("tag","initialized fan");
        AndroidNetworking.post(Urls.get_registration_data)
                .addBodyParameter("token", token)
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
                            //int count = 0 ;
                            if (jsonArray.length()==0 ) {
                                Snackbar.make(layout, "Sorry, we are facing trouble out there, please try after sometime", Snackbar.LENGTH_SHORT).show();
                                return;
                            }

                            JSONObject j = jsonArray.getJSONObject(0);

                            register_model.setReg_token(j.getString("reg_token"));
                            register_model.setEvent_name(j.getString("event_name"));
                            register_model.setEvent_id(j.getString("event_id"));
                            register_model.setPart_uid(j.getString("part_uid"));
                            register_model.setPart_name(j.getString("part_name"));
                            register_model.setPart_phone(j.getString("part_phone"));
                            register_model.setPart_email(j.getString("part_email"));
                            register_model.setPart_referrer(j.getString("part_referer"));
                            register_model.setEvent_imageUrl(j.getString("url"));
                            register_model.setParticipationStatus(j.getString("part_status"));
                            register_model.setData(j.getString("data"));
                            register_model.setComments(j.getString("comments"));
                            register_model.setPaymentStatus(j.getString("payment_status"));
                            register_model.setOrganizerEmail(j.getString("org_email"));
                            register_model.setEventType(j.getString("e_type"));
                            register_model.setTeammates(j.getString("team"));
                            register_model.setEventPriceIndividual(j.getString("e_price_indi"));
                            register_model.setEventPriceTeam(j.getString("e_price_team"));
                            register_model.setFee(j.getString("fee"));

                            try {
                                feeToBePaid = Float.parseFloat(j.getString("fee"));
                            }catch (NumberFormatException e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"Something has gone wrong, please try after sometime",Toast.LENGTH_LONG).show();
                                finish();
                            }

                            QRGEncoder encoder = new QRGEncoder(register_model.getReg_token()+":"+register_model.getEvent_name(),null,QRGContents.Type.TEXT,512);
                            try {
                                Bitmap bitmap = encoder.encodeAsBitmap();
                                imageView.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                e.printStackTrace();;
                            }
                            if (register_model.getPaymentStatus().trim().equals("Paid")){
                                payFee.setVisibility(View.GONE);
                                sendEmail.setVisibility(View.GONE);
                            }

                            textView.setText(Html.fromHtml("Please pay Rs. <b>" + register_model.getFee() +"</b> to the following account via NEFT transfer and " +
                                    "send us confirmation email along with the screenshots of payment. \nThank you!<br><br>" +
                                    "Account Name: <b>DR BVRIT PROMETHEAN</b>" +
                                    "<br>Account No.: <b>62092749799</b>" +
                                    "<br>IFSC Code: <b>SBIN0020105</b>" +
                                    "<br>Branch: Narsapur, Medak district, Telangana"));

                            e_name.setText(register_model.getEvent_name());
                            reg_status.setText(Html.fromHtml("<b>Registration Status: </b>\n\n<br>" + register_model.getParticipationStatus()));
                            data.setText(Html.fromHtml("<b>Registration data: </b>\n\n<br><br>" + register_model.getData()));
                            comments.setText(Html.fromHtml("<b>Comments: </b><br>" + register_model.getComments()));
                            payment_status.setText(Html.fromHtml("<b>Payment Status: </b><br>\n\n" + register_model.getPaymentStatus()));
//                            pariticipants.setText(Html.fromHtml("<b>Teammates: </b>\n\n" + register_model.getTeammates()));
                            or_email.setText(Html.fromHtml("<b>Organser Email: </b><br>\n\n" + register_model.getOrganizerEmail()));
                            if (Integer.parseInt(register_model.getEventType().trim()) == 1){
                                pariticipants.setText(Html.fromHtml("<b>Teammates: </b><br>\n\nIndividual participation"));
                            }else {
                                pariticipants.setText(Html.fromHtml("<b>Teammates: </b><br>\n\n"+register_model.getTeammates()));
                            }
                            registrationfee.setText(Html.fromHtml("<b>Registration amount: </b><br>\n\n"+register_model.getFee()));

                            if (register_model.getPaymentStatus().trim().equals("Not Paid")){
                                payFee.setVisibility(View.VISIBLE);
                            }else {
                                payFee.setVisibility(View.INVISIBLE);
                            }

                         //   Toast.makeText(MyEventRegnStatus.this, register_model.getPart_phone(), Toast.LENGTH_SHORT).show();

                        }catch (JSONException e){
                            Log.d("tag","exception " + e.getMessage());
                            e.printStackTrace();
                            Snackbar.make(layout,"Something's gone wrong, please try after sometime",Toast.LENGTH_SHORT).show();
                        }finally {
                            Log.d("tag","setting adapter");
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag","anerror " +anError.getResponse()+anError.getErrorBody());
                        progressDialog.dismiss();
                        Snackbar.make(layout,"Something's gone wrong, please try after sometime",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startPayment(String pay){

        Uri uri = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", "test@axisbank")
                .appendQueryParameter("pn", "Test Merchant")
                .appendQueryParameter("mc", "1234")
                .appendQueryParameter("tr", "123456789")
                .appendQueryParameter("tn", "test transaction note")
                .appendQueryParameter("am", "10.01")
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("url", "https://test.merchant.website")
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);

        if(intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, TEZ_REQUEST_CODE);
        else {
            //        startActivityForResult(intent, TEZ_REQUEST_CODE);
            Toast.makeText(this, "No package manager defined", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            // Process based on the data in response.
            Log.d("result", data.getStringExtra("Status"));
        }
    }

}
/*
     *  Email
     *  Faculty Chief
     *
 */
