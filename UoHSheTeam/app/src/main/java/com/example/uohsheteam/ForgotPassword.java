package com.example.uohsheteam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends AppCompatActivity {

    private EditText passEmail;
    private Button resetPass;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        passEmail =(EditText)findViewById(R.id.etPassEmail);
        resetPass=(Button)findViewById(R.id.btnNewPass);
        firebaseAuth = FirebaseAuth.getInstance();
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail =passEmail.getText().toString().trim();
                if(userEmail.equals("")){
                    Toast.makeText(ForgotPassword.this,"Please Enter Your Registered Email Id",Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(ForgotPassword.this,"Password Reset Email Sent!!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                            }else{
                                Toast.makeText(ForgotPassword.this,"Error In Sending Password Reset E-Mail!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}
