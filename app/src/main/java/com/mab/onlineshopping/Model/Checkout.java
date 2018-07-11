package com.mab.onlineshopping.Model;

import java.util.List;

public class Checkout {
    private String username;
    private String addressId;
    private int totalPrice;
    private List<CheckoutProduct> products;

    public Checkout() {
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

    public List<CheckoutProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CheckoutProduct> products) {
        this.products = products;
    }
}