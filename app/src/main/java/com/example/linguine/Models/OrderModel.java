package com.example.linguine.Models;

public class OrderModel {
    String mPhone, mLat, mLong, mAddress, mOrder;

    public OrderModel(String mPhone, String mLat, String mLong, String mAddress, String mOrder) {
        this.mPhone = mPhone;
        this.mLat = mLat;
        this.mLong = mLong;
        this.mAddress = mAddress;
        this.mOrder = mOrder;
    }

    public OrderModel() {
    }

    public String getmPhone() {
        return mPhone;
    }

    public String getmLat() {
        return mLat;
    }

    public String getmLong() {
        return mLong;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmOrder() {
        return mOrder;
    }
}
