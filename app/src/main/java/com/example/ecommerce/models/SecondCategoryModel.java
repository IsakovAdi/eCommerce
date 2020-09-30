package com.example.ecommerce.models;

public class SecondCategoryModel {
    private int categoryIcon;
    private String categoryName;
    private String categoryDesc;

    public SecondCategoryModel(int categoryIcon, String categoryName, String categoryDesc) {
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }
}
