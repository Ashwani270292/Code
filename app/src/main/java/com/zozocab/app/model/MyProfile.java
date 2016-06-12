package com.zozocab.app.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProfile {

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("mobile_verified")
    @Expose
    private Boolean mobileVerified;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("mobilenumber")
    @Expose
    private String mobilenumber;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("locationId")
    @Expose
    private String locationId;

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     *
     * @param picture
     * The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     *
     * @return
     * The promoCode
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     *
     * @param promoCode
     * The promo_code
     */
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    /**
     *
     * @return
     * The mobileVerified
     */
    public Boolean getMobileVerified() {
        return mobileVerified;
    }

    /**
     *
     * @param mobileVerified
     * The mobile_verified
     */
    public void setMobileVerified(Boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    /**
     *
     * @return
     * The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     * The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     * The mobilenumber
     */
    public String getMobilenumber() {
        return mobilenumber;
    }

    /**
     *
     * @param mobilenumber
     * The mobilenumber
     */
    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
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
     * The locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     *
     * @param locationId
     * The locationId
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

}