package com.example.anarg.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import stanford.androidlib.SimpleActivity;

public class MainScreenActivity extends SimpleActivity{
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        setTraceLifecycle(true);
        Toast.makeText(this, "Made by Anarghya", Toast.LENGTH_SHORT).show();
        Spinner spinner= findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.game_options));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    s = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void startIntent(String s) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("Game type",Integer.parseInt(s));
        startActivity(intent);
    }


    public void endButtonHandler(View view) {
        finish();
    }

    public void startGameHandler(View view) {
        if(s.length()!=0&&s.equals("Select a game")!=true) {
            startIntent(s);
        }
        else{
            toast("Select a proper Game Type!");
        }
    }
}
