package com.zozocab.app.model;

/**
 * Created by Ashwani on 4/10/2016.
 */
public class Estimate extends Object {

    private Price mPrice;
    private EstimateTime mTime;

    public Price getmPrice() {
        return mPrice;
    }

    public void setmPrice(Price mPrice) {
        this.mPrice = mPrice;
    }

    public EstimateTime getmTime() {
        return mTime;
    }

    public void setmTime(EstimateTime mTime) {
        this.mTime = mTime;
    }
}
