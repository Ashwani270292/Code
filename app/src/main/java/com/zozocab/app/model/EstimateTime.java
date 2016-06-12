package com.zozocab.app.model;

/**
 * Created by Ashwani on 4/10/2016.
 */
public class EstimateTime {
    private String product_id;
    private String display_name;
    private double estimate;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }
}
