package com.example.ecommerce.models;

import java.util.List;

public class GoodForFree {
    private String goodCategory;
    private String goodHeader;
    private String goodDescription;
    private List<String> goodImagesDownloadUrl;
    private String goodRegion;
    private String goodCity;
    private String goodPhoneNumber;
    private String userID;
    private String userName;

    public GoodForFree(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGoodCategory() {
        return goodCategory;
    }

    public void setGoodCategory(String goodCategory) {
        this.goodCategory = goodCategory;
    }

    public String getGoodHeader() {
        return goodHeader;
    }

    public void setGoodHeader(String goodHeader) {
        this.goodHeader = goodHeader;
    }

    public String getGoodDescription() {
        return goodDescription;
    }

    public void setGoodDescription(String goodDescription) {
        this.goodDescription = goodDescription;
    }

    public List<String> getGoodImagesDownloadUrl() {
        return goodImagesDownloadUrl;
    }

    public void setGoodImagesDownloadUrl(List<String> goodImagesDownloadUrl) {
        this.goodImagesDownloadUrl = goodImagesDownloadUrl;
    }

    public String getGoodRegion() {
        return goodRegion;
    }

    public void setGoodRegion(String goodRegion) {
        this.goodRegion = goodRegion;
    }

    public String getGoodCity() {
        return goodCity;
    }

    public void setGoodCity(String goodCity) {
        this.goodCity = goodCity;
    }

    public String getGoodPhoneNumber() {
        return goodPhoneNumber;
    }

    public void setGoodPhoneNumber(String goodPhoneNumber) {
        this.goodPhoneNumber = goodPhoneNumber;
    }
}
