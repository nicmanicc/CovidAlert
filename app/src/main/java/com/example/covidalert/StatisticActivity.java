package com.example.covidalert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity implements APIHandler.CallBack {

    RecyclerView statisticRecyclerView;
    StatisticAdapter statisticAdapter;
    List<com.example.covidalert.Statistic> statisticList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        APIHandler apiHandler = new APIHandler();
        apiHandler.requestReponse(this);
    }

    @Override
    public void success(final String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArrayCounties = jsonObject.getJSONArray("Countries");
            for (int i = 0; i < jsonArrayCounties.length(); i++) {
                JSONObject obj = jsonArrayCounties.getJSONObject(i);
                String country = obj.getString("Country");
                String confirmed = obj.getString("TotalConfirmed");
                String recovered = obj.getString("TotalRecovered");
                String death = obj.getString("TotalDeaths");
                com.example.covidalert.Statistic statistic =
                        new com.example.covidalert.Statistic(country, confirmed, recovered, death);
                statisticList.add(statistic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StatisticActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUpAdapter();;
            }
        });
    }

    private void setUpAdapter()
    {
        statisticRecyclerView = findViewById(R.id.statisticRecyclerView);
        statisticRecyclerView.addItemDecoration(new DividerItemDecoration(statisticRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        statisticAdapter = new StatisticAdapter(statisticList, StatisticActivity.this);
        statisticRecyclerView.setAdapter(statisticAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(StatisticActivity.this, LinearLayoutManager.VERTICAL, false);
        statisticRecyclerView.setLayoutManager(layoutManager);
    }
}


