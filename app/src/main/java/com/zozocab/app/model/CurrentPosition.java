package com.zozocab.app.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentPosition {

    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("lng")
    @Expose
    private Integer lng;

    /**
     *
     * @return
     * The lat
     */
    public Integer getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(Integer lat) {
        this.lat = lat;
    }

    /**
     *
     * @return
     * The lng
     */
    public Integer getLng() {
        return lng;
    }

    /**
     *
     * @param lng
     * The lng
     */
    public void setLng(Integer lng) {
        this.lng = lng;
    }

}