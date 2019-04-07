package com.example.uohsheteam;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Complaint extends AppCompatActivity implements LocationListener {
    Button send;
    String police = "7989041513";
    String security = "7004317424";
    EditText complaint;
    String complaint_msg;
    LocationManager lm;
    TextView txtLat;
    LocationManager locationManager;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        send = (Button) findViewById(R.id.btnsend);
        complaint = (EditText) findViewById(R.id.compliant_msg);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendmessage();
            }

        });
    }

    private void sendmessage() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permission == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            MyMessage();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }

    }


    private void MyMessage() {
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
        int permission1 = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);
        int permission2 = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "Sorry ", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 11);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 12);

        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //Toast.makeText(this, "Network_provider ", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    //txtLat = (TextView) findViewById(R.id.location);
                    //txtLat.setText("location"+latitude);


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
            //Toast.makeText(this, "Sorry ", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


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
        final SmsManager sms = SmsManager.getDefault();
         String message = complaint.getText().toString().trim();

        if(location2!=null) {
            message+="\nLocation url:\n";
            String lattitude = String.valueOf(location2.getLatitude());
            String longitude = String.valueOf(location2.getLongitude());
            message+= "https://www.google.com/maps/@"+lattitude+","+longitude+",13z";

        }
        final String message_to_send = message;
        FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                                              .getReference()
                                                               .child(firebaseUser.getUid());
         String phonenumber = null;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    String ph1 = dataSnapshot.child("ph1").getValue().toString();
                String ph2 = dataSnapshot.child("ph2").getValue().toString();
                String ph3 = dataSnapshot.child("ph3").getValue().toString();
                String ph4 = dataSnapshot.child("ph4").getValue().toString();
                sms.sendTextMessage(ph1, null,message_to_send, null, null);

                sms.sendTextMessage(ph2, null,message_to_send, null, null);
                sms.sendTextMessage(ph3, null,message_to_send, null, null);
                sms.sendTextMessage(ph4, null,message_to_send, null, null);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Toast.makeText(this,databaseReference.child(firebaseUser.getUid()).toString(),Toast.LENGTH_LONG).show();
        //message = message+"Location Coordinates \nlongitude:"+longitude+"\nlatitude:"+latitude;
        sms.sendTextMessage(police, null,message, null, null);

        sms.sendTextMessage(security, null,message, null, null);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                if (grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "ThankYOu", Toast.LENGTH_SHORT).show();
                    MyMessage();
                }
                else {
                    Toast.makeText(this, "Please ACCEPT", Toast.LENGTH_SHORT).show();
                }
                break;
            case 11:
                if (grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "ThankYOu", Toast.LENGTH_SHORT).show();
                    MyMessage();
                }
                else {
                    Toast.makeText(this, "Please ACCEPT", Toast.LENGTH_SHORT).show();
                }
                break;

            case 12:
                if (grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "ThankYOu", Toast.LENGTH_SHORT).show();
                    MyMessage();
                }
                else {
                    Toast.makeText(this, "Please ACCEPT", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.location);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
