package com.mab.onlineshopping.Model;

import com.google.gson.annotations.SerializedName;

public class AddToCartResponse {
    private String createdAt;
    @SerializedName("_id")
    private String id;

    public AddToCartResponse() {
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
