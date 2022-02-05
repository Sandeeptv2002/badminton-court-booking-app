package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.UUID;

public class MainActivity5 extends AppCompatActivity {
    String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        String usr_name = getIntent().getStringExtra("name");
        username = usr_name;
        String courtname = getIntent().getStringExtra("court");
        String time = getIntent().getStringExtra("time");
        TextView t1 = findViewById(R.id.textView8);
        TextView t2 = findViewById(R.id.textView9);
        t1.setText("Court : "+courtname+" court");
        t2.setText("Time : "+time);
        Button book = findViewById(R.id.button6);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity5.this,MainActivity6.class);
                UUID uniqueKey = UUID.randomUUID();
                StringBuffer booking_id = new StringBuffer(uniqueKey.toString());
                booking_id.delete(8,booking_id.length());
                intent.putExtra("bid", (Serializable) booking_id);
                intent.putExtra("name",usr_name);
                intent.putExtra("time",time);
                intent.putExtra("court",courtname);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed() {
        alertDialog();

    }
    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please select any option");
        dialog.setTitle("Do you want to cancel the booking?");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent = new Intent(MainActivity5.this,MainActivity3.class);
                        intent.putExtra("name",username);
                        startActivity(intent);
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }



}