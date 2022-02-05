package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity6 extends AppCompatActivity {
    String user_name = "";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    int dimen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        OkHttpClient okHttpClient = new OkHttpClient();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String bid = getIntent().getStringExtra("bid");
        String usr_name = getIntent().getStringExtra("name");
        String timing = getIntent().getStringExtra("time");
        String court_type = getIntent().getStringExtra("court");
        user_name = usr_name;
        TextView bookid = findViewById(R.id.textView11);
        ImageView qr = findViewById(R.id.imageView8);
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        dimen = width<height?width:height;
        dimen = dimen*3/4;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",usr_name);
            jsonObject.put("booking_id",bid);
            jsonObject.put("court",court_type);
            jsonObject.put("time",timing);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().url("https://badminton-court-booking.herokuapp.com/details").post(body).build();
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
                            qrgEncoder = new QRGEncoder(bid, null, QRGContents.Type.TEXT, dimen);
                            try {
                                bitmap = qrgEncoder.encodeAsBitmap();
                                qr.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                Log.e("Tag", e.toString());
                            }
                            Toast.makeText(getApplicationContext(),"Court booked successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Retry",Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MainActivity6.this,MainActivity.class);
        startActivity(intent);

    }


}