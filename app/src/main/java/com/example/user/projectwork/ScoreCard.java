package com.example.user.projectwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ScoreCard extends AppCompatActivity {

    RadioGroup rg;
    RadioButton rb_os,rb_ds;

    TextView sub_name,high_scr,high_scr_no,your_score,your_score_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Score-Card");

        rb_os=findViewById(R.id.radioButton);
        rb_ds=findViewById(R.id.radioButton2);
        sub_name=findViewById(R.id.textView);
        high_scr=findViewById(R.id.textView2);
        high_scr_no= findViewById(R.id.textView3);
        your_score=findViewById(R.id.textView4);
        your_score_no=findViewById(R.id.textView5);


        rb_os.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getSharedPreferences("sharedPrefs_OS", MODE_PRIVATE);
                int highScore = pref.getInt("keyHighscore",0);
                String os = rb_os.getText().toString();
                sub_name.setText(os);
                high_scr_no.setText(String.valueOf(highScore));


            }
        });

        rb_ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ds = rb_ds.getText().toString();
                sub_name.setText(ds);
                high_scr_no.setText("1");
            }
        });
    }

}
