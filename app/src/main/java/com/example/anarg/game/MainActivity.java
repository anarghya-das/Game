package com.example.anarg.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

import stanford.androidlib.SimpleActivity;

public class MainActivity extends SimpleActivity {

    private int num;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i =getIntent();
        int n=i.getIntExtra("Game type",50);
        startGame(n);
    }

    public void startGame(int n) {
        count=0;
        TextView t= findViewById(R.id.textView);
        t.setText("Welcome! Press start to \n guess a number from 0 to 100");
        EditText et= findViewById(R.id.editText);
        et.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        et.setText("");
        Toast.makeText(this, "Game Started for limit: "+n, Toast.LENGTH_SHORT).show();
        Random r = new Random();
        num = r.nextInt(n);
    }

    private void checkGuess(String s) {
        count++;
        try {
            if (s.equals(Integer.toString(num))) {
                $TV(R.id.textView).setText("You won!");
                Toast.makeText(this, "You took: " + count + " turns!", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(s) < num) {
                $TV(R.id.textView).setText("Number to guess is higher! \n Number of Turns: "+count);
            } else if (Integer.parseInt(s) > num) {
                $TV(R.id.textView).setText("Number to guess is lower! \n Number of Turns: "+count);
            }
        }catch(Exception e){
//            Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show();
            toast("Enter a valid number");
        }
    }

    public void guessHandler(View view) {
        EditText t = findViewById(R.id.editText);
        String s = t.getText().toString();
        checkGuess(s);
    }

    public void resetHandler(View view) {
        finish();
    }
}
