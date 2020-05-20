package com.example.covidalert;

//Hold information about the statistic
public class Statistic {
    private String countryName, confirmedCases, recoveredCases, deathCases, newConfirmed, newRecovered, newDeath;

    public Statistic(String countryName, String confirmedCases, String recoveredCases,
                     String deathCases, String newConfirmed, String newRecovered, String newDeath) {
        this.countryName = countryName;
        this.confirmedCases = confirmedCases;
        this.recoveredCases = recoveredCases;
        this.deathCases = deathCases;
        this.newConfirmed = newConfirmed;
        this.newRecovered = newRecovered;
        this.newDeath = newDeath;
    }

    //Getter and setter methods
    public String getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(String newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public String getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(String newRecovered) {
        this.newRecovered = newRecovered;
    }

    public String getNewDeath() {
        return newDeath;
    }

    public void setNewDeath(String newDeath) {
        this.newDeath = newDeath;
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
