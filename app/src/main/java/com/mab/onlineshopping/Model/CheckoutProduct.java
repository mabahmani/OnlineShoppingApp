package com.mab.onlineshopping.Model;

public class CheckoutProduct {
    private String productId;
    private int count;

    public CheckoutProduct() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
