package com.example.linguine.Models;

public class UserModel {
    String mName, mPhone, mMail, mAddress, mId,mLat, mLong;


    public UserModel(String mName, String mPhone, String mMail, String mAddress, String mId, String mLat, String mLong) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.mMail = mMail;
        this.mAddress = mAddress;
        this.mId = mId;
        this.mLat = mLat;
        this.mLong = mLong;
    }

    public UserModel() {
    }

    public String getmName() {
        return mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public String getmMail() {
        return mMail;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmId() {
        return mId;
    }

    public String getmLat() {
        return mLat;
    }

    public String getmLong() {
        return mLong;
    }
}
