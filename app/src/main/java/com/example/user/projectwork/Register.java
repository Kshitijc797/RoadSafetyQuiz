package com.example.user.projectwork;

import android.content.Intent;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.InputType;
import android.text.TextUtils;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity
{
    Button register ,showpass;
    EditText edit_password, edit_name, edit_email, edit_phone;
    TextView already_ac;
    ImageView icon_user;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");


        firebaseAuth = FirebaseAuth.getInstance();


        edit_name = findViewById(R.id.editText);
        edit_phone = findViewById(R.id.editText2);
        edit_email = findViewById(R.id.editText3);
        edit_password = findViewById(R.id.editText4);
        register = findViewById(R.id.button);
        showpass = findViewById(R.id.show);
        showpass.setOnClickListener(new showOrHidePassword());

        already_ac = findViewById(R.id.textView);
        icon_user = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        already_ac.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });


        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String email = edit_email.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                final String phone = edit_phone.getText().toString().trim();
                final String name = edit_name.getText().toString().trim();



               if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }


               else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter Your Phone No.!", Toast.LENGTH_SHORT).show();
                    return;
                }
               else  if (phone.length() !=10) {
                   Toast.makeText(getApplicationContext(), "Enter valid Contact Number of 10 digits", Toast.LENGTH_SHORT).show();
                   return;
               }

                else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else  if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(Register.this, Subjects.class));
                                    finish();
                                }
                            }
                        });



            }
        });

    }

    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }
    class showOrHidePassword implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (showpass.getText().toString() == "SHOW") {
                edit_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                showpass.setText("HIDE");

            } else {

                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showpass.setText("SHOW");
            }
        }
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);

    }
}
