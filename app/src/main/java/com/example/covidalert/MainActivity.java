package com.example.covidalert;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button statisticButton = findViewById(R.id.statisticsButton);
        Button informationButton = findViewById(R.id.informationButton);
        Button safetyButton = findViewById(R.id.safetyButton);
        //Add onclick listener to start new activity
        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an intent for the StatisticActivity
                Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
                //Start StatisticActivity
                MainActivity.this.startActivity(intent);
            }
        });

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an intent for the InfoActivity
               Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                //Start InfoActivity
               MainActivity.this.startActivity(intent);
            }
        });

        safetyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an intent for the SafetyActivity
                Intent intent = new Intent(MainActivity.this, SafetyActivity.class);
                //Start SafetyActivity
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
