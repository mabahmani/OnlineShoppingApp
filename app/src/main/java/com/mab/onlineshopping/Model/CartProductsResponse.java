package com.mab.onlineshopping.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartProductsResponse {
    @SerializedName("results")
    private List<CartProduct> cartProductList;

    public CartProductsResponse() {
    }

    public List<CartProduct> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<CartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }
}
