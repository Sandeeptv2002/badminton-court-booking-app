package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText name = findViewById(R.id.editTextTextPersonName2);
        String usr_name = name.getText().toString();
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        String email_id = email.getText().toString();
        EditText password = findViewById(R.id.editTextTextPassword2);
        String pass_wd = password.getText().toString();
        EditText confirm = findViewById(R.id.editTextTextPassword3);
        String conform_pass = confirm.getText().toString();
        EditText phone = findViewById(R.id.editTextPhone);
        String phn = phone.getText().toString();

        OkHttpClient okHttpClient = new OkHttpClient();

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                EditText name = findViewById(R.id.editTextTextPersonName2);
                String usr_name = name.getText().toString();
                EditText email = findViewById(R.id.editTextTextEmailAddress);
                String email_id = email.getText().toString();
                EditText password = findViewById(R.id.editTextTextPassword2);
                String pass_wd = password.getText().toString();
                EditText confirm = findViewById(R.id.editTextTextPassword3);
                String conform_pass = confirm.getText().toString();
                EditText phone = findViewById(R.id.editTextPhone);
                String phn = phone.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", usr_name);
                    jsonObject.put("email", email_id);
                    jsonObject.put("passwd", pass_wd);
                    jsonObject.put("phn", phn);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
//                RequestBody formbody = new jsonBody.Builder().add("sample", usr_name).build();
                Request request = new Request.Builder().url("https://badminton-court-booking.herokuapp.com/signup").post(body).build();
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
                        //if (response.body().string().equals("success")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Account registered successfully\nPlease login with your credentials",Toast.LENGTH_SHORT).show();
                                }
                            });
                         //}
                    }
                });

                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);

            }
        });

        }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        startActivity(intent);

    }
}
