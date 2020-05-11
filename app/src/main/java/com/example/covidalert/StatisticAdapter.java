package com.example.covidalert;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {

    private List<com.example.covidalert.Statistic> statisticList;
    private Context context;

    public StatisticAdapter(List<com.example.covidalert.Statistic> statisticList, Context context) {
        this.statisticList = statisticList;
        this.context = context;
    }

    public class StatisticViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName, confirmedView, recoveredView, deathView;
        public StatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countryName);
            confirmedView = itemView.findViewById(R.id.confirmedView);
            recoveredView = itemView.findViewById(R.id.recoveredView);
            deathView = itemView.findViewById(R.id.deathView);
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
        statisticViewHolder.confirmedView.setText(statisticList.get(i).getConfirmedCases());
        statisticViewHolder.recoveredView.setText(statisticList.get(i).getRecoveredCases());
        statisticViewHolder.deathView.setText(statisticList.get(i).getDeathCases());
    }

    @Override
    public int getItemCount() {
        return statisticList.size();
    }


}
