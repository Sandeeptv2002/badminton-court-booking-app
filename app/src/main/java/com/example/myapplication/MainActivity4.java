package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    DatePicker picker ;
    StringBuffer time_need = new StringBuffer("");
    String[] time_slot = { "09.00 AM - 10.00 AM", "10.00 AM - 11.00 AM",
            "11.00 AM - 12.00 PM", "03.00 PM - 04.00 PM","04.00 PM - 05.00 PM",
            "06.00 PM - 07.00 PM","07.00 PM - 08.00 PM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        String courtname = getIntent().getStringExtra("court");
        String usr_name = getIntent().getStringExtra("name");
        TextView court_name = findViewById(R.id.textView6);
        court_name.setText(courtname+" court");
        picker = findViewById(R.id.datePicker1);


        Spinner time = findViewById(R.id.spinner2);
        time.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time_slot);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(ad);
        OkHttpClient okHttpClient = new OkHttpClient();
        Button book = findViewById(R.id.button5);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("court", courtname);
                    jsonObject.put("time", time_need);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
//                RequestBody formbody = new jsonBody.Builder().add("sample", usr_name).build();
                Request request = new Request.Builder().url("https://badminton-court-booking.herokuapp.com/check").post(body).build();
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
                        if (response.body().string().equals("success")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity4.this,MainActivity5.class);
                                intent.putExtra("court",(Serializable) courtname);
                                intent.putExtra("time",(Serializable) time_need);
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
                                    Toast.makeText(getApplicationContext(), "Court booked already!\nPlease choose other", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });



            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        time_need.delete(0, time_need.length());
        String date =  picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
        time_need.append(date+" : ");
        time_need.append(time_slot[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // Auto-generated method stub
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity4.this,MainActivity3.class);
        startActivity(intent);

    }

}