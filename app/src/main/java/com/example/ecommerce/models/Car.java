package com.example.ecommerce.models;

import java.util.List;

public class Car {
    private String carSubCategory;
    private String carModel;
    private String carBrand;
    private String carYear;
    private String carBodyType;
    private String carFuelType;
    private String carDriveUnit;
    private String carColor;
    private String carCppType;
    private String carSteeringWheel;
    private String carCondition;
    private String carEngineCapacity;
    private String carHeader;
    private String carDescription;
    private String carPrice;
    private String carCurrency;
    private List<String> carImagesDownloadUrl;
    private String carRegion;
    private String carCity;
    private String carPhoneNumber;

    public Car(){

    }

    // Цена указана


    public Car(String carSubCategory, String carModel, String carBrand, String carYear, String carBodyType, String carFuelType, String carDriveUnit,
               String carColor, String carCppType, String carSteeringWheel, String carCondition, String carEngineCapacity, String carHeader,
               String carDescription, String carPrice, String carCurrency, List<String> carImagesDownloadUrl, String carRegion, String carCity,
               String carPhoneNumber) {
        this.carSubCategory = carSubCategory;
        this.carModel = carModel;
        this.carBrand = carBrand;
        this.carYear = carYear;
        this.carBodyType = carBodyType;
        this.carFuelType = carFuelType;
        this.carDriveUnit = carDriveUnit;
        this.carColor = carColor;
        this.carCppType = carCppType;
        this.carSteeringWheel = carSteeringWheel;
        this.carCondition = carCondition;
        this.carEngineCapacity = carEngineCapacity;
        this.carHeader = carHeader;
        this.carDescription = carDescription;
        this.carPrice = carPrice;
        this.carCurrency = carCurrency;
        this.carImagesDownloadUrl = carImagesDownloadUrl;
        this.carRegion = carRegion;
        this.carCity = carCity;
        this.carPhoneNumber = carPhoneNumber;
    }

    // Цена договорная
    public Car(String carSubCategory, String carModel, String carBrand, String carYear, String carBodyType, String carFuelType,
               String carDriveUnit, String carColor, String carCppType, String carSteeringWheel, String carCondition, String carEngineCapacity,
               String carHeader, String carDescription, List<String> carImagesDownloadUrl, String carRegion, String carCity, String carPhoneNumber) {
        this.carSubCategory = carSubCategory;
        this.carModel = carModel;
        this.carBrand = carBrand;
        this.carYear = carYear;
        this.carBodyType = carBodyType;
        this.carFuelType = carFuelType;
        this.carDriveUnit = carDriveUnit;
        this.carColor = carColor;
        this.carCppType = carCppType;
        this.carSteeringWheel = carSteeringWheel;
        this.carCondition = carCondition;
        this.carEngineCapacity = carEngineCapacity;
        this.carHeader = carHeader;
        this.carDescription = carDescription;
        this.carImagesDownloadUrl = carImagesDownloadUrl;
        this.carRegion = carRegion;
        this.carCity = carCity;
        this.carPhoneNumber = carPhoneNumber;
    }

    public String getCarSubCategory() {
        return carSubCategory;
    }

    public void setCarSubCategory(String carSubCategory) {
        this.carSubCategory = carSubCategory;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getCarBodyType() {
        return carBodyType;
    }

    public void setCarBodyType(String carBodyType) {
        this.carBodyType = carBodyType;
    }

    public String getCarFuelType() {
        return carFuelType;
    }

    public void setCarFuelType(String carFuelType) {
        this.carFuelType = carFuelType;
    }

    public String getCarDriveUnit() {
        return carDriveUnit;
    }

    public void setCarDriveUnit(String carDriveUnit) {
        this.carDriveUnit = carDriveUnit;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarCppType() {
        return carCppType;
    }

    public void setCarCppType(String carCppType) {
        this.carCppType = carCppType;
    }

    public String getCarSteeringWheel() {
        return carSteeringWheel;
    }

    public void setCarSteeringWheel(String carSteeringWheel) {
        this.carSteeringWheel = carSteeringWheel;
    }

    public String getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(String carCondition) {
        this.carCondition = carCondition;
    }

    public String getCarEngineCapacity() {
        return carEngineCapacity;
    }

    public void setCarEngineCapacity(String carEngineCapacity) {
        this.carEngineCapacity = carEngineCapacity;
    }

    public String getCarHeader() {
        return carHeader;
    }

    public void setCarHeader(String carHeader) {
        this.carHeader = carHeader;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarCurrency() {
        return carCurrency;
    }

    public void setCarCurrency(String carCurrency) {
        this.carCurrency = carCurrency;
    }

    public List<String> getCarImagesDownloadUrl() {
        return carImagesDownloadUrl;
    }

    public void setCarImagesDownloadUrl(List<String> carImagesDownloadUrl) {
        this.carImagesDownloadUrl = carImagesDownloadUrl;
    }

    public String getCarRegion() {
        return carRegion;
    }

    public void setCarRegion(String carRegion) {
        this.carRegion = carRegion;
    }

    public String getCarCity() {
        return carCity;
    }

    public void setCarCity(String carCity) {
        this.carCity = carCity;
    }

    public String getCarPhoneNumber() {
        return carPhoneNumber;
    }

    public void setCarPhoneNumber(String carPhoneNumber) {
        this.carPhoneNumber = carPhoneNumber;
    }
}
