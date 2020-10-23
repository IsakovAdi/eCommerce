package com.example.ecommerce.models;

import java.util.List;

public class Good {
    private String goodSubCategory;
    private String goodHeader;
    private String goodDescription;
    private String goodPrice;
    private String goodCurrency;
    private List<String> goodImagesDownloadUrl;
    private String goodRegion;
    private String goodCity;
    private String goodPhoneNumber;

    public Good(){}

        // With price
    public Good(String goodSubCategory, String goodHeader, String goodDescription, String goodPrice, String goodCurrency,
                List<String> goodImagesDownloadUrl, String goodRegion, String goodCity, String goodPhoneNumber) {
        this.goodSubCategory = goodSubCategory;
        this.goodHeader = goodHeader;
        this.goodDescription = goodDescription;
        this.goodPrice = goodPrice;
        this.goodCurrency = goodCurrency;
        this.goodImagesDownloadUrl = goodImagesDownloadUrl;
        this.goodRegion = goodRegion;
        this.goodCity = goodCity;
        this.goodPhoneNumber = goodPhoneNumber;
    }

        // No price
    public Good(String goodSubCategory, String goodHeader, String goodDescription, List<String> goodImagesDownloadUrl,
                String goodRegion, String goodCity, String goodPhoneNumber) {
        this.goodSubCategory = goodSubCategory;
        this.goodHeader = goodHeader;
        this.goodDescription = goodDescription;
        this.goodPrice = goodPrice;
        this.goodCurrency = goodCurrency;
        this.goodImagesDownloadUrl = goodImagesDownloadUrl;
        this.goodRegion = goodRegion;
        this.goodCity = goodCity;
        this.goodPhoneNumber = goodPhoneNumber;
    }

    public String getGoodSubCategory() {
        return goodSubCategory;
    }

    public void setGoodSubCategory(String goodSubCategory) {
        this.goodSubCategory = goodSubCategory;
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

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodCurrency() {
        return goodCurrency;
    }

    public void setGoodCurrency(String goodCurrency) {
        this.goodCurrency = goodCurrency;
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
