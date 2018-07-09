package com.mab.onlineshopping.Model;

public class CartItem extends Product {
    private int count;
    private String cartItemId;

    public CartItem() {
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
