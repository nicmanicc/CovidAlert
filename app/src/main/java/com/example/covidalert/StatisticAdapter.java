package com.example.covidalert;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {

    private List<com.example.covidalert.Statistic> statisticList;
    private Context context;
    //List used to create a copy of the statistic list received in constructor
    List<com.example.covidalert.Statistic> statisticListCopy = new ArrayList<>();

    /* Overview: Constructor that saves statistic list and makes a copy of
        it. */
    public StatisticAdapter(List<com.example.covidalert.Statistic> statisticList, Context context) {
        this.statisticList = statisticList;
        this.context = context;
        //Create a copy of the statistic list for filtering
        statisticListCopy.addAll(statisticList);
    }

    /* Overview: Creates new views (invoked by the layout manager) and
        provides a direct reference to each of the views within the statistic
        item. */
    public class StatisticViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName, confirmedView, recoveredView, deathView,
                newConfirmed, newRecovered, newDeath;
        public ImageButton mapButton;

        public StatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            mapButton = itemView.findViewById(R.id.mapButton);
            countryName = itemView.findViewById(R.id.countryName);
            confirmedView = itemView.findViewById(R.id.confirmedView);
            recoveredView = itemView.findViewById(R.id.recoveredView);
            deathView = itemView.findViewById(R.id.deathView);
            newConfirmed = itemView.findViewById(R.id.newConfirmed);
            newRecovered = itemView.findViewById(R.id.newRecovered);
            newDeath = itemView.findViewById(R.id.newDeath);
        }
    }

    /* Overview: Inflate the row layout returning an instance of the class
        StatisticViewHolder */
    @NonNull
    @Override
    public StatisticAdapter.StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.statistic_layout, viewGroup, false);
        return new StatisticAdapter.StatisticViewHolder(itemView);
    }

    /* Overview: Populates the data into each view. Also ensures that new
        cases are a non-zero value.  */
    @Override
    public void onBindViewHolder(@NonNull StatisticAdapter.StatisticViewHolder statisticViewHolder, int i) {
        //Retrieve individual country statistic values
        final String countryName = statisticList.get(i).getmCountryName();
        final String confirmedCases = statisticList.get(i).getmConfirmedCases();
        final String recoveredCases = statisticList.get(i).getmRecoveredCases();
        final String deathCases = statisticList.get(i).getmDeathCases();

        //Populate the country name into the item
        statisticViewHolder.countryName.setText(countryName);
        //Populate confirmed cases into each item
        statisticViewHolder.confirmedView.setText(StatisticActivity.formatCases(confirmedCases));
        statisticViewHolder.recoveredView.setText(StatisticActivity.formatCases(recoveredCases));
        statisticViewHolder.deathView.setText(StatisticActivity.formatCases(deathCases));

        //Concatenate "+" and new cases
        String newConfirmed = context.getResources().getString(R.string.newCases, StatisticActivity.formatCases(statisticList.get(i).getmNewConfirmed()));
        String newRecovered = context.getResources().getString(R.string.newCases, StatisticActivity.formatCases(statisticList.get(i).getmNewRecovered()));
        String newDeath = context.getResources().getString(R.string.newCases, StatisticActivity.formatCases(statisticList.get(i).getmNewDeath()));
        //Set new cases
        statisticViewHolder.newConfirmed.setText(newConfirmed);
        statisticViewHolder.newRecovered.setText(newRecovered);
        statisticViewHolder.newDeath.setText(newDeath);

        //Get the slug (used to search in google maps api)
        final String countrySlug = statisticList.get(i).getmCountrySlug();

        //Create onclick listener for the map button and start map activity
        statisticViewHolder.mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new intent for the map activity
                Intent intent = new Intent(context, MapsActivity.class);
                //Store statistic details to display in the marker window
                intent.putExtra("Slug", countrySlug);
                intent.putExtra("confirmed", confirmedCases);
                intent.putExtra("recovered", recoveredCases);
                intent.putExtra("deaths", deathCases);
                intent.putExtra("country", countryName);
                //Start map activity
                context.startActivity(intent);
            }
        });
    }

    //Get size of the statistic list
    @Override
    public int getItemCount() {
        return statisticList.size();
    }

    /* Overview: Filters a string by using the contains() function to see if
        the characters exists within the data set. */
    public void filter(String text) {
        //Clears the statistic list
        statisticList.clear();
        //If the search view is empty, add back the statistic list items
        if (text.isEmpty()) {
            statisticList.addAll(statisticListCopy);
        } else {
            text = text.toLowerCase();
            //For each item in the copied list check if it contains the string
            for (com.example.covidalert.Statistic item : statisticListCopy) {
                if (item.getmCountryName().toLowerCase().contains(text)) {
                    //If item exists, add to original statitics list
                    statisticList.add(item);
                }
            }
        }
        //Notify data set change and change the recycler view items
        notifyDataSetChanged();
    }
}
