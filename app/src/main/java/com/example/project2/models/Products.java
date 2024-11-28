package com.example.project2.models;

public class Products {
    private String name;
    private String description;
    private double price;
    private String imageURL;

    public Products() {
    }
    public Products(String name, String description, double price, String imageURL) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}