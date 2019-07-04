package com.example.user.projectwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().setTitle("Welcome");

        final SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isQuestionSet", false) != true) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isQuestionSet", false);
            editor.commit();
        }

        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() //task to be performed after delay
            {
                Intent intent1 = new Intent(Splash.this,Login.class);
                startActivity(intent1);
                finish();
            }
        },500);

    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

