package com.example.covidalert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class StatisticActivity extends AppCompatActivity implements APIHandler.CallBack {

    RecyclerView statisticRecyclerView;
    StatisticAdapter statisticAdapter;
    List<com.example.covidalert.Statistic> statisticList = new ArrayList<>();


    private String mTotalSummary, mRecoveredSummary, mDeathSummary, mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        //just added
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                statisticAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                statisticAdapter.filter(newText);
                return true;
            }
        });

        APIHandler apiHandler = new APIHandler();
        apiHandler.requestReponse(this);
    }

    @Override
    public void success(final String response) {
        try {
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);

            JSONObject jsonObject = new JSONObject(response);

            JSONObject jsonObjectGlobal = jsonObject.getJSONObject("Global");
            mTotalSummary = formatCases(jsonObjectGlobal.getString("TotalConfirmed"));;
            mRecoveredSummary = formatCases(jsonObjectGlobal.getString("TotalRecovered"));
            mDeathSummary = formatCases(jsonObjectGlobal.getString("TotalDeaths"));

            JSONArray jsonArrayCounties = jsonObject.getJSONArray("Countries");
            for (int i = 0; i < jsonArrayCounties.length(); i++) {
                JSONObject obj = jsonArrayCounties.getJSONObject(i);
                String country = obj.getString("Country") ;
                String confirmed = obj.getString("TotalConfirmed");
                String recovered = obj.getString("TotalRecovered");
                String death = obj.getString("TotalDeaths");
                String newConfirmed = obj.getString("NewConfirmed");
                String newRecovered = obj.getString("NewRecovered");
                String newDeath = obj.getString("NewDeaths");

                com.example.covidalert.Statistic statistic =
                        new com.example.covidalert.Statistic(country, confirmed, recovered, death,
                                newConfirmed, newRecovered, newDeath);
                statisticList.add(statistic);
            }

            mDate = jsonArrayCounties.getJSONObject(0).getString("Date");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        StatisticActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUpAdapter();;
                setTextView();
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

    private void setTextView()
    {
        TextView totalSummaryView = findViewById(R.id.confirmedSummary);
        TextView recoveredSummaryView = findViewById(R.id.recoveredSummary);
        TextView deathSummaryView = findViewById(R.id.deathSummary);
        TextView dateView = findViewById(R.id.dateUpdated);

        totalSummaryView.setText(mTotalSummary);
        recoveredSummaryView.setText(mRecoveredSummary);
        deathSummaryView.setText(mDeathSummary);
        dateView.setText("Last updated: " + mDate);
    }

    private String formatCases(String stringToFormat)
    {
        Number number = Integer.parseInt(stringToFormat);
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
    }
}


