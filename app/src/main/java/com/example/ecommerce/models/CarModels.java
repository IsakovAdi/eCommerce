package com.example.ecommerce.models;

public class CarModels {
    private int carIcon;
    private String carTitle;

    public CarModels(int carIcon, String carTitle) {
        this.carIcon = carIcon;
        this.carTitle = carTitle;
    }

    public int getCarIcon() {
        return carIcon;
    }

    public void setCarIcon(int carIcon) {
        this.carIcon = carIcon;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }
}
