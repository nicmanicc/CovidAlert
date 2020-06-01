package com.example.covidalert;

//Hold information about the statistic
public class Statistic {
    private String mCountryName, mConfirmedCases, mRecoveredCases, mDeathCases, mNewConfirmed, mNewRecovered, mNewDeath, mCountrySlug;

    public String getmCountrySlug() {
        return mCountrySlug;
    }

    public void setmCountrySlug(String mCountrySlug) {
        this.mCountrySlug = mCountrySlug;
    }

    public Statistic(String mCountryName, String mConfirmedCases, String mRecoveredCases, String mDeathCases, String mNewConfirmed, String mNewRecovered, String mNewDeath, String mCountrySlug) {
        this.mCountryName = mCountryName;
        this.mConfirmedCases = mConfirmedCases;
        this.mRecoveredCases = mRecoveredCases;
        this.mDeathCases = mDeathCases;
        this.mNewConfirmed = mNewConfirmed;
        this.mNewRecovered = mNewRecovered;
        this.mNewDeath = mNewDeath;
        this.mCountrySlug = mCountrySlug;
    }

    public String getmCountryName() {
        return mCountryName;
    }

    public void setmCountryName(String mCountryName) {
        this.mCountryName = mCountryName;
    }

    public String getmConfirmedCases() {
        return mConfirmedCases;
    }

    public void setmConfirmedCases(String mConfirmedCases) {
        this.mConfirmedCases = mConfirmedCases;
    }

    public String getmRecoveredCases() {
        return mRecoveredCases;
    }

    public void setmRecoveredCases(String mRecoveredCases) {
        this.mRecoveredCases = mRecoveredCases;
    }

    public String getmDeathCases() {
        return mDeathCases;
    }

    public void setmDeathCases(String mDeathCases) {
        this.mDeathCases = mDeathCases;
    }

    public String getmNewConfirmed() {
        return mNewConfirmed;
    }

    public void setmNewConfirmed(String mNewConfirmed) {
        this.mNewConfirmed = mNewConfirmed;
    }

    public String getmNewRecovered() {
        return mNewRecovered;
    }

    public void setmNewRecovered(String mNewRecovered) {
        this.mNewRecovered = mNewRecovered;
    }

    public String getmNewDeath() {
        return mNewDeath;
    }

    public void setmNewDeath(String mNewDeath) {
        this.mNewDeath = mNewDeath;
    }
}
