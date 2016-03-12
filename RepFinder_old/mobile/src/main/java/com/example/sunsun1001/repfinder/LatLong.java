package com.example.sunsun1001.repfinder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Activity;
import android.location.*;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;
import java.util.Locale;
import java.util.List;
import java.io.IOException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import android.text.TextUtils;
import android.util.Log;
import java.io.InputStreamReader;
import java.net.URL;


public class LatLong extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    private TextView myAddress;
    public double Lat;
    public double Long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lat_long);

        mLatitudeText = (TextView) findViewById(R.id.textViewLat);
        mLongitudeText = (TextView) findViewById(R.id.textViewLong);
        myAddress = (TextView)findViewById(R.id.address);
        buildGoogleApiClient();

        mGoogleApiClient.connect(); // connect to Google API
        //new GeoCoding("1888 Berkeley Way 94703").execute();

    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }



    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            Lat = (mLastLocation.getLatitude());
            Long = (mLastLocation.getLongitude());
            //Toast.makeText(this, String.valueOf(Long), Toast.LENGTH_SHORT).show();

            Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
            try {

                // From Latitude and Longitude:

                List<Address> addresses = geocoder.getFromLocation(Lat, Long , 1);



                // From Zip Code:
                //List<Address> addresses = geocoder.getFromLocationName("95023", 1);

                if(addresses != null && addresses.size() > 0) {

                    Address fetchedAddress = addresses.get(0);
                    StringBuilder strAddress = new StringBuilder();

                    for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                    }

                    myAddress.setText("I am at: " + "kk");

                }

                else
                    myAddress.setText("No location found..!");

            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
            }
        }
        if (mLastLocation == null) {
            Toast.makeText(this, "LocationIsNull", Toast.LENGTH_SHORT).show();
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //10 seconds
        mLocationRequest.setFastestInterval(5000); //5 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setSmallestDisplacement(1); //1 meter

    }


    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

}