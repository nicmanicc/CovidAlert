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
    List<com.example.covidalert.Statistic> statisticListCopy = new ArrayList<>();

    public StatisticAdapter(List<com.example.covidalert.Statistic> statisticList, Context context) {
        this.statisticList = statisticList;
        this.context = context;

        //just added
        statisticListCopy.addAll(statisticList);
        Log.v("test", statisticListCopy.toString());

    }

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

    @NonNull
    @Override
    public StatisticAdapter.StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.statistic_layout, viewGroup, false);
        return new StatisticAdapter.StatisticViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticAdapter.StatisticViewHolder statisticViewHolder, int i) {
        statisticViewHolder.countryName.setText(statisticList.get(i).getCountryName());
        statisticViewHolder.confirmedView.setText(formatCases(statisticList.get(i).getConfirmedCases()));
        statisticViewHolder.recoveredView.setText(formatCases(statisticList.get(i).getRecoveredCases()));
        statisticViewHolder.deathView.setText(formatCases(statisticList.get(i).getDeathCases()));

        String newConfirmedAmount = statisticList.get(i).getNewConfirmed();
        String newRecoveredAmount = statisticList.get(i).getNewRecovered();
        String newDeathAmount = statisticList.get(i).getNewDeath();

        //Check if previous days cases aren't 0
        if(Integer.parseInt(newConfirmedAmount) != 0) {
            statisticViewHolder.newConfirmed.setText("+" + formatCases(newConfirmedAmount));
        }
        if(Integer.parseInt(newRecoveredAmount) != 0) {
            statisticViewHolder.newRecovered.setText("+" + formatCases(newRecoveredAmount));
        }
        if(Integer.parseInt(newDeathAmount) != 0) {
            statisticViewHolder.newDeath.setText("+" + formatCases(newDeathAmount));
        }

    }

    @Override
    public int getItemCount() {
        return statisticList.size();
    }

    public void filter(String text) {
        statisticList.clear();
        if(text.isEmpty()) {
            statisticList.addAll(statisticListCopy);
        } else {
            text = text.toLowerCase();
            for (com.example.covidalert.Statistic item : statisticListCopy) {
                if(item.getCountryName().toLowerCase().contains(text)) {
                    statisticList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    private String formatCases(String stringToFormat)
    {
        Number number = Integer.parseInt(stringToFormat);
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
    }

}
