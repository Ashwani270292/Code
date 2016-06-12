package com.zozocab.app.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by metro on 09-06-2016.
 */
public class PushSettings {

    @SerializedName("apns")
    @Expose
    private Apns apns;
    @SerializedName("gcm")
    @Expose
    private Gcm gcm;

    /**
     * @return The apns
     */
    public Apns getApns() {
        return apns;
    }

    /**
     * @param apns The apns
     */
    public void setApns(Apns apns) {
        this.apns = apns;
    }

    /**
     * @return The gcm
     */
    public Gcm getGcm() {
        return gcm;
    }

    /**
     * @param gcm The gcm
     */
    public void setGcm(Gcm gcm) {
        this.gcm = gcm;
    }

}