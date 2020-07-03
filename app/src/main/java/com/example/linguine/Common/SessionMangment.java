package com.example.linguine.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.linguine.Activities.Login;

import java.util.HashMap;

public class SessionMangment {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private int PRIVATE_MODE = 0;
    private Intent mIntent;


    private static final String PREF_NAME = "Lenguine";

    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "key_id";
    public static final String KEY_FNAME = "key_fname";
    public static final String KEY_EMAIL = "key_email";
    public static final String KEY_PHONE = "key_phone";
    public static final String KEY_IMAGE = "key_image";
    public static final String KEY_LAT = "LAT";
    public static final String KEY_LONG = "LONG";
    public static final String KEY_IMAGEPATH = "key_image_path";

    /**
     * Constructor
     **/
    public SessionMangment(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * Save User Details
     **/
    public void createLoginSession(Boolean status,
                                   String id,
                                   String fname,
                                   String email,
                                   String phone,
                                   String Lat,
                                   String Long
    ) {

        mEditor.putBoolean(IS_LOGIN, status);
        mEditor.putString(KEY_ID, id);
        mEditor.putString(KEY_FNAME, fname);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_PHONE, phone);
        mEditor.putString(KEY_LAT, Lat);
        mEditor.putString(KEY_LONG, Long);
        mEditor.commit();
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> users = new HashMap<String, String>();

        users.put(KEY_ID, mSharedPreferences.getString(KEY_ID, null));
        users.put(KEY_FNAME, mSharedPreferences.getString(KEY_FNAME, null));

        users.put(KEY_EMAIL, mSharedPreferences.getString(KEY_EMAIL, null));
        users.put(KEY_PHONE, mSharedPreferences.getString(KEY_PHONE, null));
        users.put(KEY_IMAGE, mSharedPreferences.getString(KEY_IMAGE, null));
        users.put(KEY_LAT, mSharedPreferences.getString(KEY_LAT, null));
        users.put(KEY_LONG, mSharedPreferences.getString(KEY_LONG, null));
        users.put(KEY_IMAGEPATH, mSharedPreferences.getString(KEY_IMAGEPATH, null));

        return users;
    }


    public void clearData() {
        mEditor.clear();
        mEditor.commit();
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
        mIntent = new Intent(mContext, Login.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent);
    }

    public void updatename(String name) {
        mEditor.putString(KEY_FNAME, name);
        mEditor.commit();
    }

    public void updateLocation(String Lat,
                               String Long) {
        mEditor.putString(KEY_LAT, Lat);
        mEditor.putString(KEY_LONG, Long);
        mEditor.commit();

    }

    public void updatEmial(String name) {
        mEditor.putString(KEY_EMAIL, name);
    }

    public void SavePhoto(String bitmap) {
        mEditor.putString(KEY_IMAGE, bitmap);
        mEditor.commit();
    }

    /**
     * Quick check for login
     **/
    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
