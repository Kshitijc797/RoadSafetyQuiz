package com.example.user.projectwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Frst_Test_Level extends AppCompatActivity {
Button OS_easy_btn;
    TextView Highscore;

    private static final int REQUEST_CODE_QUIZ=1;

    public static final  String SHARED_PREFS = "sharedPrefs_OS";
    public static final  String KEY_HIGHSCORE = "keyHighscore";
    private int highscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frst_test_level);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Test-1");

        OS_easy_btn =findViewById(R.id.os_easy_quiz);
        Highscore = findViewById(R.id.highscore);
        loadhighscore();
        OS_easy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frst_Test_Level.this,Frst_Test_Quiz.class);
                startActivityForResult(intent,REQUEST_CODE_QUIZ);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ)
        {
            if (resultCode == RESULT_OK)
            {
                int score = data.getIntExtra(Frst_Test_Quiz.EXTRA_SCORE,0);
                if (score > highscore)
                {
                    updatehighscore(score);
                }
            }
        }
    }

    private void loadhighscore ()
    {
        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = pref.getInt(KEY_HIGHSCORE,0);
        Highscore.setText("HIGHSCORE "+highscore);
    }

    private void updatehighscore(int highscorenew)
    {
        highscore =highscorenew;
        Highscore.setText("HIGHSCORE "+highscore);

        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.commit();
    }
}
