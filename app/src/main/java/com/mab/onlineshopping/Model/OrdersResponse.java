package com.mab.onlineshopping.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersResponse {
    @SerializedName("results")
    private List<Order> orderList;

    public OrdersResponse() {
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
