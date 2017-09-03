package com.example.admin.pureindia;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener
, LocationListener{

    private GoogleMap mMap;
    private GoogleApiClient mClient;
    String userName;

    private double lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        userName = getIntent().getExtras().getString("username");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first

        // The activity is either being restarted or started for the first time
        // so this is where we should make sure that GPS is enabled
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            // Create a dialog here that requests the user to enable GPS, and use an intent
            // with the android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS action
            // to take the user to the Settings screen to enable GPS when they click "OK"
            displayPromptForEnablingGPS(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
    }

    public static void displayPromptForEnablingGPS(
            final Activity activity)
    {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Enable either GPS or any other location"
                + " service to find current location.  Click OK to go to"
                + " location services settings to let you do so.";


        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        });

        builder.create().show();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//         defaultShow();

        mClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mClient.connect();

        //detectPosition();


    }


    LocationRequest locationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

       locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
      locationRequest.setInterval(2000); // update after every 2 seconds

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mClient);
        if(mLastLocation != null)
        try {
            forDefaultZoom(mLastLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       LocationServices.FusedLocationApi.requestLocationUpdates(mClient, locationRequest, this);
      //  LocationServices.FusedLocationApi.getLastLocation(mClient);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }


    private void goToLocation(double a, double b){
        LatLng ll = new LatLng(a,b);

        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);

        mMap.moveCamera(update);
        locationZoom(a,b,1);
    }

    private void locationZoom(double a, double b, float k){

        LatLng ll = new LatLng(a,b);

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, k);

        mMap.moveCamera(update);
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();
      //  Toast.makeText(getApplicationContext(), " lat = "+lat+" lon = "+lon, Toast.LENGTH_SHORT).show();
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
        mMap.animateCamera(update);

       try {
            mark(lat, lon);
        } catch (Exception e) {

        }

        //detectPosition();
    }

    Marker marker;

    public void mark(double lat, double lon) throws Exception{

        if(marker != null)
            marker.remove();

        Geocoder gc = new Geocoder(this);

            List<Address> list = gc.getFromLocation(lat, lon, 15);

            Address address = list.get(0);

        MarkerOptions options = new MarkerOptions()
                .title(address.getLocality())
                .position(new LatLng(lat, lon))
                .snippet("Latitude = "+lat+" Longitude = "+lon);

        marker = mMap.addMarker(options);
    }

    private void forDefaultZoom(Location location){

        lat = location.getLatitude();
        lon = location.getLongitude();
        //  Toast.makeText(getApplicationContext(), " lat = "+lat+" lon = "+lon, Toast.LENGTH_SHORT).show();
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
        mMap.animateCamera(update);

        try {
            mark(lat, lon);
        } catch (Exception e) {

        }
    }

    public void goTo(View view) {

     //   Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WasteOptionsActivity.class);
        intent.putExtra("Latitude", lat);
        intent.putExtra("Longitude", lon);
        intent.putExtra("username", userName);
        startActivity(intent);
    }
}
