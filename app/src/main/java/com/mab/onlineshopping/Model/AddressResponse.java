package com.mab.onlineshopping.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponse {
    @SerializedName("results")
    private List<Address> addressList;

    public AddressResponse() {
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
