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
        //Add onclick listener to start new activity
        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an intent for the StatisticActivity
                Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
                //Start StatisticActivity
                startActivity(intent);
            }
        });
    }
}
