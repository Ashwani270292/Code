package com.zozocab.app.model;

/**
 * Created by Ashwani on 4/10/2016.
 */
public class Price {

    private String product_id;
    private String currency_code;
    private String display_name;
    private String estimate;
    private double low_estimate;
    private double high_estimate;
    private double surge_multiplier;
    private double duration;
    private double distance;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public double getLow_estimate() {
        return low_estimate;
    }

    public void setLow_estimate(double low_estimate) {
        this.low_estimate = low_estimate;
    }

    public double getHigh_estimate() {
        return high_estimate;
    }

    public void setHigh_estimate(double high_estimate) {
        this.high_estimate = high_estimate;
    }

    public double getSurge_multiplier() {
        return surge_multiplier;
    }

    public void setSurge_multiplier(double surge_multiplier) {
        this.surge_multiplier = surge_multiplier;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
