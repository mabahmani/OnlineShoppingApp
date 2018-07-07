package com.mab.onlineshopping.Model;

import com.google.gson.annotations.SerializedName;

public class ProductId {
    @SerializedName("_id")
    private String id;

    public ProductId() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
