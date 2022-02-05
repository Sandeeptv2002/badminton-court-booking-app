package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// implements onClickListener for the onclick behaviour of button
public class MainActivity7 extends AppCompatActivity implements View.OnClickListener {
    Button scanBtn;
    TextView messageText, messageFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        // referencing and initializing
        // the button and textviews
        scanBtn = findViewById(R.id.scanBtn);
        messageText = findViewById(R.id.textContent);
        messageFormat = findViewById(R.id.textFormat);

        // adding listener to the button
        scanBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // we need to create the object
        // of IntentIntegrator class
        // which is the class of QR library
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                OkHttpClient okHttpClient = new OkHttpClient();
                String id = intentResult.getContents();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("bid", id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
//                RequestBody formbody = new jsonBody.Builder().add("sample", usr_name).build();
                Request request = new Request.Builder().url("https://badminton-court-booking.herokuapp.com/get_ticket").post(body).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "server down", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    JSONObject myResponse = null;
                                    try {
                                        myResponse = new JSONObject(response.body().string());
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                    StringBuffer data = new StringBuffer();

                                    try{
                                        String uname = myResponse.getString("user_name");
                                        String id = myResponse.getString("booking_id");
                                        String time = myResponse.getString("timing");
                                        String type = (String) myResponse.get("court_type");
                                        data.append("User Name - "+myResponse.getString("user_name"));
                                        data.append("\nBooking ID - "+myResponse.getString("booking_id"));
                                        data.append("\nTiming - "+myResponse.getString("timing"));
                                        data.append("\nCourt Type - "+myResponse.getString("court_type"));
                                        Intent intent = new Intent(MainActivity7.this,MainActivity8.class);
                                        intent.putExtra("name",uname);
                                        intent.putExtra("court",(Serializable) type);
                                        intent.putExtra("time",(Serializable) time);
                                        intent.putExtra("bid",id);
                                        startActivity(intent);
                                    }catch(JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                        String final_data = new String(data.toString());

                                        messageText.setText(final_data);


                                }
                            });
                    }
                });

                messageFormat.setText(intentResult.getFormatName());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
