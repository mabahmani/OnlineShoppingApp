package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.AddAddress;
import com.mab.onlineshopping.Model.AddressResponse;
import com.mab.onlineshopping.Model.UserUsername;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAddressesController {
    private OnlineShoppingApi.GetAddressesCallBack getAddressesCallBack;

    public GetAddressesController(OnlineShoppingApi.GetAddressesCallBack getAddressesCallBack) {
        this.getAddressesCallBack = getAddressesCallBack;
    }

    public void start(String accessToken, final UserUsername userUsername) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<AddressResponse> call = onlineShoppingApi.getAddresses(accessToken, userUsername);
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                getAddressesCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                getAddressesCallBack.onFailure(t.toString());
            }
        });
    }
}
