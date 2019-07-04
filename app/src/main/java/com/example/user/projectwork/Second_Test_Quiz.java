package com.example.user.projectwork;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Second_Test_Quiz extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    public static final long COUNTDOWN_IN_MILLIS = 30000;

    private TextView Score,Question_count,Count_down,Question;
    private Button Confirm,btn_finish;
    private RadioGroup rg;
    private RadioButton Option_1,Option_2,Option_3,Option_4;

    private ColorStateList textColorDefaultrb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    public long timeLeftInMillis;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scnd_test_quiz);

        Score = findViewById(R.id.score);
        Question_count = findViewById(R.id.question_count);
        Count_down = findViewById(R.id.count_down);
        rg = findViewById(R.id.radioGroup);
        Question = findViewById(R.id.question);
        Option_1 = findViewById(R.id.option1);
        Option_2 = findViewById(R.id.option2);
        Option_3 = findViewById(R.id.option3);
        Option_4 = findViewById(R.id.option4);
        //this will store default color of radiobutton in this variable
        textColorDefaultrb = Option_1.getTextColors();
        textColorDefaultCd = Count_down.getTextColors();
        Confirm = findViewById(R.id.confirm);
        btn_finish = findViewById(R.id.finish);

        getSupportActionBar().setTitle("Test-2");
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();

        btn_finish.setVisibility(View.GONE);

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Option_1.isChecked() && !Option_2.isChecked() && !Option_3.isChecked() && !Option_4.isChecked())
                {
                    Snackbar.make(v,"Please select an Option First",Snackbar.LENGTH_LONG).show();
                }
                else if (Option_1.isChecked())
                {
                    Option_1.setTextColor(Color.RED);
                    Option_2.setTextColor(Color.GREEN);
                    Option_3.setTextColor(Color.RED);
                    Option_4.setTextColor(Color.RED);
                    Snackbar.make(v,"Wrong Answer",Snackbar.LENGTH_LONG).show();                }
                else if (Option_2.isChecked())
                {
                    Option_1.setTextColor(Color.RED);
                    Option_2.setTextColor(Color.GREEN);
                    Option_3.setTextColor(Color.RED);
                    Option_4.setTextColor(Color.RED);
                    Snackbar.make(v,"Correct Answer",Snackbar.LENGTH_LONG).show();
                    Score.setText("Score: 1");
                }
                else if (Option_3.isChecked())
                {
                    Option_1.setTextColor(Color.RED);
                    Option_2.setTextColor(Color.GREEN);
                    Option_3.setTextColor(Color.RED);
                    Option_4.setTextColor(Color.RED);
                    Snackbar.make(v,"Wrong Answer",Snackbar.LENGTH_LONG).show();

                }
                else if (Option_4.isChecked())
                {
                    Option_1.setTextColor(Color.RED);
                    Option_2.setTextColor(Color.GREEN);
                    Option_3.setTextColor(Color.RED);
                    Option_4.setTextColor(Color.RED);
                    Snackbar.make(v,"Wrong Answer",Snackbar.LENGTH_LONG).show();
                }
                countDownTimer.cancel();
                btn_finish.setVisibility(View.VISIBLE);
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second_Test_Quiz.this,Subjects.class);
                startActivity(intent);
            }
        });

        }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        Count_down.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            Count_down.setTextColor(Color.RED);
        } else {
            Count_down.setTextColor(textColorDefaultCd);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
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
        Toast.makeText(this, "Please click BACK again to exit Quiz", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);

    }
}
