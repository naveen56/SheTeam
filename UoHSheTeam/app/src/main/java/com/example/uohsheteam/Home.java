package com.example.uohsheteam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Home extends AppCompatActivity {

    Button register;
    Button complaint;
    Button location;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        register = (Button) findViewById(R.id.button_manage);
        complaint = (Button) findViewById(R.id.button_complaint);
        location = (Button) findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerfunction();
            }

        });
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaintfunction();
            }

        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationfunction();
            }

        });
    }

    private void locationfunction() {
        sendmessage();
        Intent intent = new Intent(Home.this, MapsActivity.class);

        startActivity(intent);
    }

    private void sendmessage() {
        int permission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permission2 = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SOrry ", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 11);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 12);

        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, "Network_provicder ", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "SOrry ", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {


                }


                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }


                @Override
                public void onProviderEnabled(String s) {

                }


                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
        List<String> providers = locationManager.getProviders(true);
        Location location2=null;
        for(String provider:providers) {
            location2 = locationManager.getLastKnownLocation(provider);
            if(location2!=null)
                break;
        }
        SmsManager sms = SmsManager.getDefault();
        String message = "I lost my location";

        if(location2!=null) {
            message+="\nLocation url:\n";
            String lattitude = String.valueOf(location2.getLatitude());
            String longitude = String.valueOf(location2.getLongitude());
            message+= "https://www.google.com/maps/@"+lattitude+","+longitude+",13z";

        }

        //message = message+"Location Coordinates \nlongitude:"+longitude+"\nlatitude:"+latitude;

        String security="8985245095";
        sms.sendTextMessage(security, null,message, null, null);


    }

    private void complaintfunction() {
        Intent intent = new Intent(Home.this, Complaint.class);
        startActivity(intent);
    }

    private void registerfunction() {
        Intent intent = new Intent(Home.this, Activity2.class);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                if (grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "ThankYOu", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(this, "Please ACCEPT", Toast.LENGTH_SHORT).show();
                }
                break;
            case 11:
                if (grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "ThankYOu", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(this, "Please ACCEPT", Toast.LENGTH_SHORT).show();
                }
                break;

            case 12:
                if (grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "ThankYOu", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(this, "Please ACCEPT", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
