package com.example.anarg.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int num;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Made by Anarghya", Toast.LENGTH_SHORT).show();
    }

    public void buttonHandler(View view) {
        count=0;
        TextView t= findViewById(R.id.textView);
        t.setText("Welcome! Press start to \n guess a number from 0 to 100");
        EditText et= findViewById(R.id.editText);
        et.setText("");
        Toast.makeText(this, "Game Started", Toast.LENGTH_SHORT).show();
        Random r = new Random();
        num = r.nextInt(100);
    }

    private void checkGuess(String s) {
        count++;
        TextView t = findViewById(R.id.textView);
        try {
            if (s.equals(Integer.toString(num))) {
                t.setText("You won!");
                Toast.makeText(this, "You took: " + count + " turns!", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(s) < num) {
                t.setText("Number to guess is higher! \n Number of Turns: "+count);
            } else if (Integer.parseInt(s) > num) {
                t.setText("Number to guess is lower! \n Number of Turns: "+count);
            }
        }catch(Exception e){
            Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    public void guessHandler(View view) {
        EditText t = findViewById(R.id.editText);
        String s = t.getText().toString();
        checkGuess(s);
    }
}
