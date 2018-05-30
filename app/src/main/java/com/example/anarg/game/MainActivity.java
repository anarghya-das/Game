package com.example.anarg.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

import stanford.androidlib.SimpleActivity;

public class MainActivity extends SimpleActivity {

    private int num;
    private int gameLimit;
    private int count;
    private int highScore;
    private static  final int DEFAULT_HIGH_SCORE=9999;
    private static MediaPlayer mp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTraceLifecycle(true);
        if(savedInstanceState!=null){
//            Log.v("Count while getting",Integer.toString(savedInstanceState.getInt("score")));
            SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
            highScore = pref.getInt("highScore", DEFAULT_HIGH_SCORE);
            setHighScores();
            resumeGame(savedInstanceState.getInt("num"),savedInstanceState.getInt("score"),savedInstanceState.getString("note"));
//            mp.start();
        }
        else {
            Intent i = getIntent();
            gameLimit = i.getIntExtra("Game type", 50);
            SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
            highScore = pref.getInt("highScore", DEFAULT_HIGH_SCORE);
            count = 0;
            setHighScores();
            startGame(gameLimit);
            Toast.makeText(this, "Game Started for limit: " + gameLimit, Toast.LENGTH_SHORT).show();
            mp = MediaPlayer.create(this, R.raw.got);
            mp.start();
            mp.setLooping(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score", count);
        Log.v("Count while passsing", Integer.toString(count));
        outState.putInt("num", num);
        outState.putString("note", (String) $TV(R.id.textView).getText());
//        outState.putInt("gameLimit",gameLimit);
    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }

    private void setHighScores(){
        if(highScore!=DEFAULT_HIGH_SCORE) {
            $TV(R.id.high_score).setText("High Score: " + highScore);
        }
    }

    private void setScore(){
        $TV(R.id.user_score).setText("Score: "+count);
    }

    public void startGame(int n) {
        $TV(R.id.user_score).setText("Score: "+count);
        TextView t= findViewById(R.id.textView);
        t.setText("Welcome! Press start to \n guess a number from 0 to "+n);
        EditText et= findViewById(R.id.editText);
        et.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        et.setText("");
        Random r = new Random();
        num = r.nextInt(n);
        $TV(R.id.button2).setEnabled(true);

    }

    public void resumeGame(int num,int c,String s){
        $TV(R.id.user_score).setText("Score: "+c);
        this.num=num;
        $TV(R.id.textView).setText(s);
    }


    private void checkGuess(String s) {
        try {
            int n=Integer.parseInt(s);
            if(n<0){
                toast("Enter a number greater than 0!");
                $ET(R.id.editText).setText("");
            }
            else {
                count++;
                if (n==num) {
                    $TV(R.id.textView).setText("You won!");
                    $TV(R.id.button2).setEnabled(false);
                    setScore();
                    highScoreCheck(count);
//                    Toast.makeText(this, "You took: " + count + " turns!", Toast.LENGTH_SHORT).show();
                } else if (n < num) {
                    setScore();
                    $TV(R.id.textView).setText("Number to guess is higher!\n Last Number entered: "+s);
                    $ET(R.id.editText).setText("");
                } else if (n > num) {
                    setScore();
                    $TV(R.id.textView).setText("Number to guess is lower!\n Last Number entered: "+s);
                    $ET(R.id.editText).setText("");
                }
            }
        } catch(Exception e){
//            Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show();
            toast("Enter a valid number");
            $ET(R.id.editText).setText("");
        }
    }

    private void highScoreCheck(int n){
        if(n<highScore){
            highScore=n;
            setHighScores();
            toast("Congratulations! You set a new HighScore!");
            SharedPreferences preferences=getSharedPreferences("myPref",MODE_PRIVATE);
            SharedPreferences.Editor prefEditor= preferences.edit();
            prefEditor.putInt("highScore",highScore);
            prefEditor.apply();
        }
    }

    public void guessHandler(View view) {
        EditText t = findViewById(R.id.editText);
        String s = t.getText().toString();
        checkGuess(s);
    }

    public void restartHandler(View view) {
        startGame(gameLimit);
    }

    public void stopMusic(View view) {
        if($B(R.id.mute_button).getText().equals("Mute Music")) {
            mp.setVolume(0, 0);
            $B(R.id.mute_button).setText("Play Music");
        }
        else if($B(R.id.mute_button).getText().equals("Play Music")){
            mp.setVolume(1, 1);
            $B(R.id.mute_button).setText("Mute Music");

        }
    }
}
