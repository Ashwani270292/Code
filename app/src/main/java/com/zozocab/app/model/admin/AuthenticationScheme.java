package com.zozocab.app.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by metro on 09-06-2016.
 */
public class AuthenticationScheme {

    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("credential")
    @Expose
    private Credential credential;

    /**
     * @return The scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * @param scheme The scheme
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * @return The credential
     */
    public Credential getCredential() {
        return credential;
    }

    /**
     * @param credential The credential
     */
    public void setCredential(Credential credential) {
        this.credential = credential;
    }

}