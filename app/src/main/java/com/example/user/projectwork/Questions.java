package com.example.user.projectwork;

/**
 * Created by Let's Get It Started on 8/11/2018.
 */

public class Questions
{
    private String question;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;
    private int answer_no;

    public Questions() {}

    public Questions(String question, String option_1, String option_2, String option_3, String option_4, int answer_no) {
        this.question = question;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;
        this.answer_no = answer_no;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_1() {
        return option_1;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public void setOption_3(String option_3) {
        this.option_3 = option_3;
    }

    public String getOption_4() {
        return option_4;
    }

    public void setOption_4(String option_4) {
        this.option_4 = option_4;
    }

    public int getAnswer_no() {
        return answer_no;
    }

    public void setAnswer_no(int answer_no) {
        this.answer_no = answer_no;
    }
}

