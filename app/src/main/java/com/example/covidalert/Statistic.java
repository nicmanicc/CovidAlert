package com.example.covidalert;

public class Statistic {
    private String countryName, confirmedCases, recoveredCases, deathCases;

    public Statistic(String countryName, String confirmedCases, String recoveredCases, String deathCases) {
        this.countryName = countryName;
        this.confirmedCases = confirmedCases;
        this.recoveredCases = recoveredCases;
        this.deathCases = deathCases;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(String confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public String getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(String recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public String getDeathCases() {
        return deathCases;
    }

    public void setDeathCases(String deathCases) {
        this.deathCases = deathCases;
    }
}
