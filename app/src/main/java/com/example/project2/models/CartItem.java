package com.example.project2.models;

public class CartItem {
    private String name;
    private double price;
    private int quantity;
    private String imageURL;

    public CartItem() {
        
    }

    public CartItem(String name, double price, int quantity, String imageURL) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}