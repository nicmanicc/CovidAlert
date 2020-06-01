package com.example.covidalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
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

    //Variable declarations for views on activity
    RecyclerView statisticRecyclerView;
    StatisticAdapter statisticAdapter;

    //List used to hold each statistic in the recycler view
    List<com.example.covidalert.Statistic> statisticList = new ArrayList<>();

    //Variables for the global statistics
    private String mTotalSummary, mRecoveredSummary, mDeathSummary;

    //Variable for the date the statistics were updated
    private String mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        //Retrieve SearchView from activity
        final SearchView searchView = findViewById(R.id.searchView);
        //Set text hint for the search view
        searchView.setQueryHint("Example: \"Australia\"");
        //Check for text being typed in search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Filter recycler view if text is being typed
            @Override
            public boolean onQueryTextSubmit(String query) {
                statisticAdapter.filter(query);
                //Close the keyboard after search button pressed
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                statisticAdapter.filter(newText);
                return true;
            }
        });
        //Create API handler object
        APIHandler apiHandler = new APIHandler();
        //Ask API handler to request a response from covid-19 API
        apiHandler.requestReponse(this);

        //Get refresh button from activity
        final Button refreshButton = findViewById(R.id.refreshButton);
        //Set the refresh button to invisible
        refreshButton.setVisibility(View.INVISIBLE);

        //Create a new handler object
        final Handler handler = new Handler();
        //Create a new runnable to run after 5 seconds has elapsed
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Get the total summary cases view from activity
                TextView totalSummaryView = findViewById(R.id.confirmedSummary);
                //Check if the total summary cases view is empty (meaning data hasn't loaded)
                if (totalSummaryView.getText().toString().equals("")) {
                    //Set refresh button to visible
                    refreshButton.setVisibility(View.VISIBLE);
                    //Create an on click listener for the refresh button
                    refreshButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Get this activities intent
                            Intent intent = getIntent();
                            //Finish using activity
                            finish();
                            //Start the same activity ("Restarting it")
                            startActivity(intent);
                        }
                    });
                }
            }
        }, 5000); //Delay 5000ms => 5 seconds
    }
    /* Overview: Used to handle the string response received from OkHTTP
    request in the APIHandler class. Done by retrieving the global and individual
    country statistics from the JSON object created from the response. The
    global statistics is used to update the text views in the activity and the
    country statistics is used to update the recycler view in the activity. */
    @Override
    public void onSuccess(final String response) {

        //Error handling
        try {
            //Sets progress wheel to invisible (everything will begin to load in)
            ProgressBar progressWheel = findViewById(R.id.progressBar);
            progressWheel.setVisibility(View.INVISIBLE);

            //Create a JSON object from the response
            JSONObject jsonObjectResponse = new JSONObject(response);

            //Pull the global JSON object from jsonObjectResponse
            JSONObject jsonObjectGlobal = jsonObjectResponse.getJSONObject("Global");
            //Save and format (1000 => 1,000) the global statistics from jsonObjectGlobal
            mTotalSummary = formatCases(jsonObjectGlobal.getString("TotalConfirmed"));
            mRecoveredSummary = formatCases(jsonObjectGlobal.getString("TotalRecovered"));
            mDeathSummary = formatCases(jsonObjectGlobal.getString("TotalDeaths"));
            //Pull the countries JSON array from jsonObjectResponse
            JSONArray jsonArrayCountries = jsonObjectResponse.getJSONArray("Countries");

            //Retrieve individual country statistics from each object within the array
            for (int i = 0; i < jsonArrayCountries.length(); i++) {
                //Create a JSON object from position i in the array
                JSONObject obj = jsonArrayCountries.getJSONObject(i);
                //Save individual country statistic information
                String country = obj.getString("Country") ;
                String confirmed = obj.getString("TotalConfirmed");
                String recovered = obj.getString("TotalRecovered");
                String death = obj.getString("TotalDeaths");
                String newConfirmed = obj.getString("NewConfirmed");
                String newRecovered = obj.getString("NewRecovered");
                String newDeath = obj.getString("NewDeaths");
                String countrySlug = obj.getString("Slug");

                //Create a statistic object from the previously saved statistic information
                com.example.covidalert.Statistic statistic =
                        new com.example.covidalert.Statistic(country, confirmed, recovered, death,
                                newConfirmed, newRecovered, newDeath, countrySlug);
                //Add statistic to statisticList
                statisticList.add(statistic);
            }
            //Save the date and time the statistics were last updated.
            mDate = jsonArrayCountries.getJSONObject(0).getString("Date");
            //Cut string to only include the date
            mDate = mDate.substring(0, 10);
            //concatenate date with "Last updated: "
            mDate = this.getResources().getString(R.string.date, mDate);
        //Print exception
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Update the views via main thread
        StatisticActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Updates recycler view
                updateRecyclerView();
                //Updates text views within the activity
                updateTextView();
            }
        });
    }
    /* Overview: Updates the recycler view by adding an adapter and
    decorative elements (line divider and layout manager). */
    private void updateRecyclerView()
    {
        //Save recycler view from activity
        statisticRecyclerView = findViewById(R.id.statisticRecyclerView);
        //Add a horizontal line dividing each item in the recycler view
        statisticRecyclerView.addItemDecoration(new DividerItemDecoration(statisticRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        //A layout manager to display each item vertically in the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(StatisticActivity.this, LinearLayoutManager.VERTICAL, false);
        //Create an adapter with the statistics list holding individual country statistics
        statisticAdapter = new StatisticAdapter(statisticList, StatisticActivity.this);
        //Set the recycler view adapter
        statisticRecyclerView.setAdapter(statisticAdapter);
        //Set the recycler view layout manager
        statisticRecyclerView.setLayoutManager(layoutManager);
    }
    /* Overview: Updates the activities text views with the global statistics
    and date updated received from the OkHTTP response. */
    private void updateTextView()
    {
        //Save text views from activity
        TextView totalSummaryView = findViewById(R.id.confirmedSummary);
        TextView recoveredSummaryView = findViewById(R.id.recoveredSummary);
        TextView deathSummaryView = findViewById(R.id.deathSummary);
        TextView dateView = findViewById(R.id.dateUpdated);
        //Set the text for each text view in activity
        totalSummaryView.setText(mTotalSummary);
        recoveredSummaryView.setText(mRecoveredSummary);
        deathSummaryView.setText(mDeathSummary);
        dateView.setText(mDate);
    }
    /* Overview: Adds symbols to numbers to make them more readable.
    The symbol will depends on what location they're in.
    E.g. 1000 => 1,000 (for Aus) */
    public static String formatCases(String stringToFormat)
    {
        //Turn the string into a number
        Number number = Integer.parseInt(stringToFormat);
        //Format the number using NumberFormat and return
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
    }
}


