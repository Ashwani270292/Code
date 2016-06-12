package com.zozocab.app.model.profile;


import java.util.UUID;

/**
 * Created by Ashwani on 4/10/2016.
 */
public class Profile {

    private UUID mUuid;
    private String firsName, lastName,image_url;
    private String emailId,mobile_number,promocode;
    private boolean is_mobile_verified;
    private boolean is_rider;


    public UUID getmUuid() {
        return mUuid;
    }

    public void setmUuid(UUID mUuid) {
        this.mUuid = mUuid;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public boolean is_mobile_verified() {
        return is_mobile_verified;
    }

    public void setIs_mobile_verified(boolean is_mobile_verified) {
        this.is_mobile_verified = is_mobile_verified;
    }

    public boolean is_rider() {
        return is_rider;
    }

    public void setIs_rider(boolean is_rider) {
        this.is_rider = is_rider;
    }
}
