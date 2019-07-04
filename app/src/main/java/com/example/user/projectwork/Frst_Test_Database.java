package com.example.user.projectwork;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

/**
 * Created by Let's Get It Started on 8/11/2018.
 */

public class Frst_Test_Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProjectWork.db";
    public static final int DATABASE_VERSION = 1;
    public static final String OS_TABLE_NAME ="Os_Question";
    public static final String COLUMN_QUESTION = "questions";
    public static final String COLUMN_OPTION1 = "option_1";
    public static final String COLUMN_OPTION2 = "option_2";
    public static final String COLUMN_OPTION3 = "option_3";
    public static final String COLUMN_OPTION4 = "option_4";
    public static final String COLUMN_ANSWER_NO = "answer_no";
    SharedPreferences sharedPreferences;


    public Frst_Test_Database(Context context) {
        super(context, DATABASE_NAME , null ,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String OS_QUESTION_TABLE="CREATE TABLE " + OS_TABLE_NAME + " (" + "ID" +" NUMBER PRIMARY KEY," + COLUMN_QUESTION + " TEXT," + COLUMN_OPTION1 + " TEXT," + COLUMN_OPTION2 + " TEXT," + COLUMN_OPTION3 + " TEXT," + COLUMN_OPTION4 + " TEXT," + COLUMN_ANSWER_NO + " NUMBER);";

        sqLiteDatabase.execSQL(OS_QUESTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +OS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    //adding questions to SQLite database
    public void fill_OS_QuestionsTable ()
    {
        Questions q1 = new Questions("Q. It is permissible to exceed the speed limit…?" ," Under no circumstances" , " When making an overtake" , " On empty roads" , " In perfect condition" ,1);
        addQuestion(q1);

        Questions q2 = new Questions("Q. Once you have obtained your learner's licence, it is valid for how long?" ," Six months" , " One year" , " Eight months" , " Four months" ,1);
        addQuestion(q2);

        Questions q3 = new Questions("Q. You see a pedestrian waiting at a crossing ahead. You should…?" ," Slow down and wawe them over" , " Come to a complete stop and wait for them to cross" , " Hoot to warn them not to cross" , " Speed up to be pass as fast as possible" ,2);
        addQuestion(q3);

        Questions q4 = new Questions("Q. A state driving license is valid for use…?" ," Only in the state of issue" , " Anywhere in india" , " Anywhere in India or Pakistan" , " Anywhere in world" ,2);
        addQuestion(q4);

        Questions q5 = new Questions("Q. How many vehicle lengths should you leave to the vehicle in front when driving at 45 km/h?" ," 2" , " 4" , " 3" , " 7" ,1);
        addQuestion(q5);

        Questions q6 = new Questions("Q. When you are driving near a school, you should do what?" ," Turns your lights on" , " Accelerate to leave the area quickly" , " Sounds your horn constantly" , " Reduce your speed" ,4);
        addQuestion(q6);

        Questions q7 = new Questions("Q. You see a blind person holding up their white cane at the roadside. What should you do?" ," Move to the other side of road to avoid them" , " Accelerate to pass them quickly" , " Stop and let them cross" , " Sout a warning form your window" ,3);
        addQuestion(q7);

        Questions q8 = new Questions("Q. The best driving technique for dealing with slippery roads is to…?" ," Change up gear" , " Drive as quick as possible" , " Constantly apply the brakes" , " Change down the gear" ,4);
        addQuestion(q8);

        Questions q9 = new Questions("Q. You are driving on a dark road at night when you see another vehicle approaching. What should you do?" ," Sound your horn long and hard" , " Switch to low beam headlights" , " Switch to high beam headlights" , " Turn off your headlights" ,2);
        addQuestion(q9);

        Questions q10 = new Questions("Q. A PUCC (Pollution Under Control Certificate) is valid for how long from the date of issue?" ," Eight months" , " Four months" , " Twelve months" , " Eleven months" ,3);
        addQuestion(q10);
    }

    private void addQuestion(Questions OSQuestions)
    {
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUESTION, OSQuestions.getQuestion());
        cv.put(COLUMN_OPTION1, OSQuestions.getOption_1());
        cv.put(COLUMN_OPTION2, OSQuestions.getOption_2());
        cv.put(COLUMN_OPTION3, OSQuestions.getOption_3());
        cv.put(COLUMN_OPTION4, OSQuestions.getOption_4());
        cv.put(COLUMN_ANSWER_NO, OSQuestions.getAnswer_no());

        sqLiteDatabase.insert(OS_TABLE_NAME, null,cv);
    }

    //retrieving questions from database
    public  List<Questions> getAllQuestions() {
        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        List<Questions> questionList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " +OS_TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(COLUMN_QUESTION)));
                question.setOption_1(c.getString(c.getColumnIndex(COLUMN_OPTION1)));
                question.setOption_2(c.getString(c.getColumnIndex(COLUMN_OPTION2)));
                question.setOption_3(c.getString(c.getColumnIndex(COLUMN_OPTION3)));
                question.setOption_4(c.getString(c.getColumnIndex(COLUMN_OPTION4)));
                question.setAnswer_no(c.getInt(c.getColumnIndex(COLUMN_ANSWER_NO)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
