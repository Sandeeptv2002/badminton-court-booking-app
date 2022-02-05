package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity8 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        String username = getIntent().getStringExtra("name");
        String court = getIntent().getStringExtra("court");
        String time = getIntent().getStringExtra("time");
        String id = getIntent().getStringExtra("bid");
        TextView name = findViewById(R.id.textView13);
        name.setText("Name : "+username);
        TextView bid = findViewById(R.id.textView14);
        bid.setText("Booking ID : "+id);
        TextView court_type = findViewById(R.id.textView12);
        court_type.setText("Court type : "+court);
        TextView timing = findViewById(R.id.textView15);
        timing.setText(time);

    }
}