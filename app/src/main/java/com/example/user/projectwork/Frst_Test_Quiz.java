package com.example.user.projectwork;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Frst_Test_Quiz extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    public static final long COUNTDOWN_IN_MILLIS = 30000;


private TextView Score,Question_count,Count_down,Question;
private Button Confirm;
private RadioGroup rg;
private RadioButton Option_1,Option_2,Option_3,Option_4;

private ColorStateList textColorDefaultrb;
private ColorStateList textColorDefaultCd;

private CountDownTimer countDownTimer;
public long timeLeftInMillis;

private List<Questions> OSQuestionsList;
private int questionCounter;
private int questionCountTotal;
private Questions currentQuestion;

private int score;
private boolean answered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frst_test_quiz);
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

        getSupportActionBar().setTitle("Test-1");

        final Frst_Test_Database db = new Frst_Test_Database(this);

        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isQuestionSet", false) == false) {
            db.fill_OS_QuestionsTable();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isQuestionSet", true);
            editor.commit();
        }

        OSQuestionsList = db.getAllQuestions();

        questionCountTotal = OSQuestionsList.size();
        Collections.shuffle(OSQuestionsList);

        showNextQuestion();

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (Option_1.isChecked() || Option_2.isChecked() || Option_3.isChecked() || Option_4.isChecked()) {
                        checkAnswer();
                    } else {
                        Snackbar.make(v,"Please Select an Option",Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        Option_1.setTextColor(textColorDefaultrb);
        Option_2.setTextColor(textColorDefaultrb);
        Option_3.setTextColor(textColorDefaultrb);
        Option_4.setTextColor(textColorDefaultrb);
        rg.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = OSQuestionsList.get(questionCounter);

            Question.setText(currentQuestion.getQuestion());
            Option_1.setText(currentQuestion.getOption_1());
            Option_2.setText(currentQuestion.getOption_2());
            Option_3.setText(currentQuestion.getOption_3());
            Option_4.setText(currentQuestion.getOption_4());

            questionCounter++;
            Question_count.setText("Question: " + questionCounter + "/" +OSQuestionsList.size());
            answered = false;
            Confirm.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            OSQuestionsList.clear();
            finish();
        }
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
                checkAnswer();
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

    private void checkAnswer() {
        answered = true;
        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rg.getCheckedRadioButtonId());
        int answerNr = rg.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswer_no()) {
            score++;
            Score.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution() {
        Option_1.setTextColor(Color.RED);
        Option_2.setTextColor(Color.RED);
        Option_3.setTextColor(Color.RED);
        Option_4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswer_no()) {
            case 1:
                Option_1.setTextColor(Color.GREEN);
                break;
            case 2:
                Option_2.setTextColor(Color.GREEN);
                break;
            case 3:
                Option_3.setTextColor(Color.GREEN);
                break;
            case 4:
                Option_4.setTextColor(Color.GREEN);
                break;
        }

        if (questionCounter < questionCountTotal) {
            Confirm.setText("Next");
        } else {
            Confirm.setText("Finish");
            finishQuiz();
        }
    }

    private void finishQuiz() {
        Intent result_intent = new Intent();
        result_intent.putExtra(EXTRA_SCORE,score);
        setResult(RESULT_OK,result_intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        finishQuiz();
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
                finish();
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);

    }
}
