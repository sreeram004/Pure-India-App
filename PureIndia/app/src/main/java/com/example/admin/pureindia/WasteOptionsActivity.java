package com.example.admin.pureindia;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.content.*;



public class WasteOptionsActivity extends AppCompatActivity {
    String userName;
    @Override
       protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_options);

        final double latitude = getIntent().getExtras().getDouble("Latitude");
        final double longitude = getIntent().getExtras().getDouble("Longitude");
        userName = getIntent().getExtras().getString("username");

        String[] wastes = {"Solid Waste", "Liquid Waste", "Improper Drainage", "Recyclable Wastes", "Non Degradable Wastes", "Uncleaned Trash", "Other Wastes"};

        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wastes);

        ListView listView = (ListView) findViewById(R.id.wl);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String wasteType = String.valueOf(parent.getItemAtPosition(position));

               // Toast.makeText(getApplicationContext(), ""+wasteType+" at Latitude : "+latitude+" Longitude : "+longitude, Toast.LENGTH_SHORT).show();
             // sendData(latitude, longitude, wasteType);


                pop(longitude, latitude, wasteType);

              //  Toast.makeText(getApplicationContext(), "Thank You, Complaint Reported", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void pop(double longitude, double latitude, String wasteType) {
        Intent intent = new Intent(this, popupActivity.class);
        intent.putExtra("Longitude", ""+longitude);
        intent.putExtra("Latitude", ""+latitude);
        intent.putExtra("WasteType", ""+wasteType);
        intent.putExtra("username", userName);
        startActivity(intent);
    }





}





