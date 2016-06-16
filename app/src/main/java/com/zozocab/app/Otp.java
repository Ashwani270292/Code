package com.zozocab.app;

/**
 * Created by metro on 12-06-2016.
 */
public class Otp {


    private String userid;
    private String mobile;
    private String code;

    public Otp(String userid, String mobile, String code) {
        this.userid = userid;
        this.mobile = mobile;
        this.code = code;
    }


    public String getUserid() {
        return userid;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCode() {
        return code;
    }
}
