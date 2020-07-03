package com.example.linguine.Models;

public class CategoryModel {
String mName , mImage;

    public CategoryModel(String mName, String mImage) {
        this.mName = mName;
        this.mImage = mImage;
    }

    public String getmName() {
        return mName;
    }

    public String getmImage() {
        return mImage;
    }

    public CategoryModel() {
    }
}
