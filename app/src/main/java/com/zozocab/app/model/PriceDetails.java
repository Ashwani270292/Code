package com.zozocab.app.model;

/**
 * Created by Ashwani on 4/10/2016.
 */
public class PriceDetails {

    private double[] service_fees;
    private double cost_per_minute;
    private String distance_unit;
    private double minimum;
    private double cost_per_distance;
    private double base;
    private double cancellation_fee;
    private String currency_code;

    public double[] getService_fees() {
        return service_fees;
    }

    public void setService_fees(double[] service_fees) {
        this.service_fees = service_fees;
    }

    public double getCost_per_minute() {
        return cost_per_minute;
    }

    public void setCost_per_minute(double cost_per_minute) {
        this.cost_per_minute = cost_per_minute;
    }

    public String getDistance_unit() {
        return distance_unit;
    }

    public void setDistance_unit(String distance_unit) {
        this.distance_unit = distance_unit;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getCost_per_distance() {
        return cost_per_distance;
    }

    public void setCost_per_distance(double cost_per_distance) {
        this.cost_per_distance = cost_per_distance;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getCancellation_fee() {
        return cancellation_fee;
    }

    public void setCancellation_fee(double cancellation_fee) {
        this.cancellation_fee = cancellation_fee;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
}
