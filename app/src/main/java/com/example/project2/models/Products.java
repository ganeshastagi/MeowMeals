package com.example.project2.models;

public class Products {
    private String name;
    private String description;
    private double price;
    private String imageURL;

    private String fullDescription;

    public Products() {

    }

    public Products(String name, String description, double price, String imageURL, String fullDescription) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.fullDescription = fullDescription;
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

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }
}