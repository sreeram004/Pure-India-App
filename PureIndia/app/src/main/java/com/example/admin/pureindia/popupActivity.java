package com.example.admin.pureindia;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class popupActivity extends AppCompatActivity {

    Button confirm;
    Button back;
    String longitude, latitude, wasteType, userName;
    String date, time;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_popup);

        confirm = (Button) findViewById(R.id.okbutton);
        back = (Button) findViewById(R.id.backbutton);

        calendar = Calendar.getInstance();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 latitude = getIntent().getExtras().getString("Latitude");
                 longitude = getIntent().getExtras().getString("Longitude");
                 wasteType = getIntent().getExtras().getString("WasteType");
                 userName = getIntent().getExtras().getString("username");

                date = ""+calendar.get(Calendar.DATE)+" - "+calendar.get(Calendar.MONTH)+" - "+calendar.get(Calendar.YEAR);
                time = ""+calendar.get(Calendar.HOUR)+" : "+calendar.get(Calendar.MINUTE)+ " : "+calendar.get(Calendar.SECOND);
          //      Toast.makeText(getApplicationContext(), ""+userName+, Toast.LENGTH_SHORT).show();
                new Send().execute();
                Toast.makeText(getApplicationContext(), "Thank You, Complaint Reported", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int) (height*.5));

    }

    class Send extends AsyncTask<String, Void,Long > {


        protected Long doInBackground(String... urls) {

            String userLongitude= ""+longitude;
            String userLatitude= ""+latitude;
            String userWasteType=""+wasteType;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("ADDRESS OF THE DOMAIN PHP CODE");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("USER_NAME", userName));
                nameValuePairs.add(new BasicNameValuePair("DATE", date));
                nameValuePairs.add(new BasicNameValuePair("TIME", time));
                nameValuePairs.add(new BasicNameValuePair("LATITUDE", userLatitude));
                nameValuePairs.add(new BasicNameValuePair("LONGITUDE", userLongitude));
                nameValuePairs.add(new BasicNameValuePair("WASTE_TYPE", userWasteType));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
            return null;

        }
        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }
}
