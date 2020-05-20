package com.example.covidalert;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        public StatisticViewHolder(@NonNull View itemView) {
            super(itemView);
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
        //Populate the country name into the item
        statisticViewHolder.countryName.setText(statisticList.get(i).getCountryName());
        //Populate confirmed cases into each item
        statisticViewHolder.confirmedView.setText(formatCases(statisticList.get(i).getConfirmedCases()));
        statisticViewHolder.recoveredView.setText(formatCases(statisticList.get(i).getRecoveredCases()));
        statisticViewHolder.deathView.setText(formatCases(statisticList.get(i).getDeathCases()));
        //Retrieve new cases from statistic list
        String newConfirmedAmount = statisticList.get(i).getNewConfirmed();
        String newRecoveredAmount = statisticList.get(i).getNewRecovered();
        String newDeathAmount = statisticList.get(i).getNewDeath();

        //Check if previous days cases aren't 0
        //Populate new cases into each item
        if (Integer.parseInt(newConfirmedAmount) != 0) {
            statisticViewHolder.newConfirmed.setText("+" + formatCases(newConfirmedAmount));
        }
        if (Integer.parseInt(newRecoveredAmount) != 0) {
            statisticViewHolder.newRecovered.setText("+" + formatCases(newRecoveredAmount));
        }
        if (Integer.parseInt(newDeathAmount) != 0) {
            statisticViewHolder.newDeath.setText("+" + formatCases(newDeathAmount));
        }
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
                if (item.getCountryName().toLowerCase().contains(text)) {
                    //If item exists, add to original statitics list
                    statisticList.add(item);
                }
            }
        }
        //Notify data set change and change the recycler view items
        notifyDataSetChanged();
    }

    /* Overview: Adds symbols to numbers to make them more readable.
   The symbol will depends on what location they're in.
   E.g. 1000 => 1,000 (for Aus) */
    private String formatCases(String stringToFormat) {
        //Turn the string into a number
        Number number = Integer.parseInt(stringToFormat);
        //Format the number using NumberFormat and return
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
    }
}
