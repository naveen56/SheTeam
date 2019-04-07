package com.example.uohsheteam;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity2 extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    Button add;
    EditText ph1,ph2,ph3,ph4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        add = (Button)findViewById(R.id.btnsubmit);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerfunction();
            }

        });;

    }

    private void registerfunction() {
        ph1 = (EditText) findViewById(R.id.etPh1);
        ph2 = (EditText) findViewById(R.id.etPh2);
        ph3 = (EditText) findViewById(R.id.etPh3);
        ph4 = (EditText) findViewById(R.id.etPh4);
        String phone1 = ph1.getText().toString();
        String phone2 = ph2.getText().toString();
        String phone3 = ph3.getText().toString();
        String phone4 = ph4.getText().toString();
        PhoneNumbers pn = new PhoneNumbers(phone1,phone2,phone3,phone4);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference.child(firebaseUser.getUid()).setValue(pn);
        Toast.makeText(this,"Numbers Registered", Toast.LENGTH_LONG).show();
    }


}

