package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jetbrains.annotations.NotNull;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText username = findViewById(R.id.editTextTextPersonName);
        EditText password = findViewById(R.id.editTextTextPassword);
        Button button = findViewById(R.id.button2);
        Button login = findViewById(R.id.button7);
        Button ticket = findViewById(R.id.button);
        OkHttpClient okHttpClient = new OkHttpClient();
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,MainActivity7.class);
                    startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                String usr_name = username.getText().toString();
                String pass_wd = password.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name",usr_name);
                    jsonObject.put("passwd",pass_wd);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder().url("https://badminton-court-booking.herokuapp.com/login").post(body).build();
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
                    String responseString;
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                        if (response.body().string().equals("success")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Logged in successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this,MainActivity3.class);
                                    intent.putExtra("name",usr_name);
                                    startActivity(intent);
                                }
                            });
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Please enter your correct login details",Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    }
                });



            }
        });
    }
}