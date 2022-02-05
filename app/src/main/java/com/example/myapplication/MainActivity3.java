package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usr_name = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_main3);
        ImageView wooden = findViewById(R.id.imageView);
        ImageView synthetic = findViewById(R.id.imageView5);
        ImageView acrylic = findViewById(R.id.imageView4);
        ImageView cement = findViewById(R.id.imageView6);
        Button book_court = findViewById(R.id.button4);
        StringBuffer court_name = new StringBuffer("");
        wooden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                court_name.delete(0, court_name.length());
                synthetic.setBackgroundResource(R.color.grey);
                acrylic.setBackgroundResource(R.color.grey);
                cement.setBackgroundResource(R.color.grey);
                wooden.setBackgroundResource(R.drawable.red);
                court_name.append("Wooden");
            }
        });
        synthetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                court_name.delete(0, court_name.length());
                wooden.setBackgroundResource(R.color.grey);
                acrylic.setBackgroundResource(R.color.grey);
                cement.setBackgroundResource(R.color.grey);
                synthetic.setBackgroundResource(R.drawable.red);
                court_name.append("Synthetic");
            }
        });

        acrylic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                court_name.delete(0, court_name.length());
                synthetic.setBackgroundResource(R.color.grey);
                wooden.setBackgroundResource(R.color.grey);
                cement.setBackgroundResource(R.color.grey);
                acrylic.setBackgroundResource(R.drawable.red);
                court_name.append("Acrylic");
            }
        });

        cement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                court_name.delete(0, court_name.length());
                synthetic.setBackgroundResource(R.color.grey);
                acrylic.setBackgroundResource(R.color.grey);
                wooden.setBackgroundResource(R.color.grey);
                cement.setBackgroundResource(R.drawable.red);
                court_name.append("Cement");
            }
        });

        book_court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((court_name.toString()).equals("")) {
                    Toast.makeText(getApplicationContext(),"Please select your court",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                    intent.putExtra("court", (Serializable) court_name);
                    intent.putExtra("name",usr_name);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity3.this,MainActivity.class);
        startActivity(intent);

    }
}