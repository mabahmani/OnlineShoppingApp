package com.mab.onlineshopping.Model;

public class CartItem extends Product {
    private int count;

    public CartItem() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
