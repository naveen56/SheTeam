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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity {

    private EditText userName, userPassword1, userPassword2, userPhone, userEmail;
    private Button SignUp;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SignUp = (Button) findViewById(R.id.btnRegister);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String user_Email= userEmail.getText().toString().trim();
                    String user_Pass =userPassword1.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_Email,user_Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this,"Register Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent( Register.this, MainActivity.class));

                            } else{
                                Toast.makeText(Register.this,"Register Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

    }
    private void setupUIViews(){
        userName=(EditText)findViewById(R.id.etUserName);
        userPassword1=(EditText)findViewById(R.id.etPassword1);
        userPassword2=(EditText)findViewById(R.id.etPassword2);
        userEmail=(EditText)findViewById(R.id.etEmail);
        userPhone=(EditText)findViewById(R.id.etPhone);
        SignUp=(Button)findViewById(R.id.btnRegister);
    }
    private Boolean validate(){
        Boolean result=false;
        String name= userName.getText().toString();
        String confirmPassword=userPassword2.getText().toString();
        String password=userPassword1.getText().toString();
        String email=userEmail.getText().toString();
        String phone=userPhone.getText().toString();

        if(name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || phone.isEmpty()){

            Toast.makeText(this, "Please Enter All The Details", Toast.LENGTH_SHORT).show();
        }else
            result=true;
        return result;

    }
}

