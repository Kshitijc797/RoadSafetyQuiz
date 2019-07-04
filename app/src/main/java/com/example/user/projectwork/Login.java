package com.example.user.projectwork;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
   private  EditText  inputPassword;
   AutoCompleteTextView inputEmail;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button  btnLogin,showpass;
    private boolean showState = false; // password is hidden by default

    String values[];
    private TextView forgot_pass,registernow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Login");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, Subjects.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);



        inputEmail = findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        btnLogin = (Button) findViewById(R.id.login);
        showpass = (Button) findViewById(R.id.show);
        showpass.setOnClickListener(new showOrHidePassword());
        forgot_pass = findViewById(R.id.forgot);
        registernow = findViewById(R.id.notregister);

        values=getResources().getStringArray(R.array.data);
        String values[] = getResources().getStringArray(R.array.data);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
        inputEmail.setAdapter(adapter);
        inputEmail.setThreshold(1);



        //Get Firebase auth instance
     //   auth = FirebaseAuth.getInstance();

        registernow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });


       forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Forgot_Email.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError("Password too short, enter minimum 6 characters!");
                                    } else {
                                        Toast.makeText(Login.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(Login.this, Subjects.class);
                                    startActivity(intent);
                                    finish();
                                    inputEmail.setText("");
                                    inputPassword.setText("");

                                }
                            }
                        });
            }
        });
    }

    class showOrHidePassword implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (showState == false) {
                inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                showpass.setText("HIDE");
                showState = true;

            } else {

                inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showpass.setText("SHOW");
                showState = false;
            }
        }
    }

}