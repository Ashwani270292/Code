package com.zozocab.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverModel {

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("cartype")
    @Expose
    private String cartype;
    @SerializedName("isbooked")
    @Expose
    private Boolean isbooked;
    @SerializedName("is_on_ride")
    @Expose
    private Boolean isOnRide;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("earned_amount")
    @Expose
    private String earnedAmount;
    @SerializedName("customer_mobile")
    @Expose
    private String customerMobile;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     * The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The cartype
     */
    public String getCartype() {
        return cartype;
    }

    /**
     *
     * @param cartype
     * The cartype
     */
    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    /**
     *
     * @return
     * The isbooked
     */
    public Boolean getIsbooked() {
        return isbooked;
    }

    /**
     *
     * @param isbooked
     * The isbooked
     */
    public void setIsbooked(Boolean isbooked) {
        this.isbooked = isbooked;
    }

    /**
     *
     * @return
     * The isOnRide
     */
    public Boolean getIsOnRide() {
        return isOnRide;
    }

    /**
     *
     * @param isOnRide
     * The is_on_ride
     */
    public void setIsOnRide(Boolean isOnRide) {
        this.isOnRide = isOnRide;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The earnedAmount
     */
    public String getEarnedAmount() {
        return earnedAmount;
    }

    /**
     *
     * @param earnedAmount
     * The earned_amount
     */
    public void setEarnedAmount(String earnedAmount) {
        this.earnedAmount = earnedAmount;
    }

    /**
     *
     * @return
     * The customerMobile
     */
    public String getCustomerMobile() {
        return customerMobile;
    }

    /**
     *
     * @param customerMobile
     * The customer_mobile
     */
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

}