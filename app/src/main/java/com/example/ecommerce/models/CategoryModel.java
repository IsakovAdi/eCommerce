package com.example.ecommerce.models;

public class CategoryModel {

    private int categoryIcon;
    private String categoryName;
    private String categoryDescription;

    public CategoryModel(int categoryIcon, String categoryName) {
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
    }

    public CategoryModel(int categoryIcon, String categoryName, String categoryDescription) {
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
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
}
