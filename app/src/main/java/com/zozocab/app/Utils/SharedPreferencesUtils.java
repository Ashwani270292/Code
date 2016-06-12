package com.zozocab.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by metro on 28-05-2016.
 */
public class SharedPreferencesUtils {

    public static final String PREFS = "zozo";
    public static final String USERNMAME = "zozo";
    public static final String HASH = "zozo";
    public static final String EMAILID = "zozo";
    public static final String PHONENO = "phone";
    private final Context context;


    private SharedPreferences preferences;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

    }

    public void saveUsername(String username) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNMAME, username);
        editor.apply();
    }

    public void saveEmail(String email) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAILID, email);
        editor.apply();
    }

    public void savePhone(String phone) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PHONENO, phone);
        editor.apply();
    }

    public void savePasswordHash(String hash) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(HASH, hash);
        editor.apply();
    }


    public String getUsername() {
        String username = preferences.getString(USERNMAME, "");
        return username;

    }

    public String saveEmail() {
        String email = preferences.getString(EMAILID, "");
        return email;
    }

    public String getPhone() {
        String email = preferences.getString(PHONENO, "");
        return email;
    }

    public String getPasswordHash() {

        String hash = preferences.getString(HASH, "");
        return hash;
    }

    public boolean isExists() {
        if (!getUsername().isEmpty()) {
            return true;
        }
        return false;
    }

}
