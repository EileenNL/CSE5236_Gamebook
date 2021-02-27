package com.example.cse5236;

public class Prompt {

    int mPrompt;
    int mOption1;
    int mOption2;
    Prompt mOption1Prompt;
    Prompt mOption2Prompt;

    public Prompt (int prompt, int option1, int option2, Prompt option1Prompt, Prompt option2Prompt){
        mPrompt = prompt;
        mOption1 = option1;
        mOption2 = option2;
        mOption1Prompt = option1Prompt;
        mOption2Prompt = option2Prompt;
    }

    public int getPrompt() {
        return mPrompt;
    }

    public int getOption1() {
        return mOption1;
    }

    public int getOption2() {
        return mOption2;
    }

    public void setPrompt(int prompt) {
        mPrompt = prompt;
    }

    public void setOption1(int option1) {
        mOption1 = option1;
    }

    public void setOption2(int option2) {
        mOption2 = option2;
    }

    public Prompt getOption1Prompt() {
        return mOption1Prompt;
    }

    public void setOption1Prompt(Prompt option1Prompt) {
        mOption1Prompt = option1Prompt;
    }

    public Prompt getOption2Prompt() {
        return mOption2Prompt;
    }

    public void setOption2Prompt(Prompt option2Prompt) {
        mOption2Prompt = option2Prompt;
    }
}
