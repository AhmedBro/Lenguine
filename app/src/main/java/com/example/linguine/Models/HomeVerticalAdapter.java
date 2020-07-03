package com.example.linguine.Models;

public class HomeVerticalAdapter {

    String mName, mPrice, mPrice2, mImage;


    public HomeVerticalAdapter() {
    }

    public HomeVerticalAdapter(String mName, String mPrice, String mPrice2, String mimage) {
        this.mName = mName;

        this.mPrice = mPrice;

        this.mImage = mimage;
        this.mPrice2 = mPrice2;
    }

    public String getmName() {
        return mName;
    }


    public String getmPrice() {
        return mPrice;
    }

    public String getmPrice2() {
        return mPrice2;
    }

    public String getmImage() {
        return mImage;
    }
}
