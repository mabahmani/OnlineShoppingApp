package com.mab.onlineshopping.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("_id")
    private String id;
    private String username;
    private String addressId;
    private int totalPrice;
    private String createdAt;
    private List<CheckoutProduct> products;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<CheckoutProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CheckoutProduct> products) {
        this.products = products;
    }
}
