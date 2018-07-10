package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.AddAddress;
import com.mab.onlineshopping.Model.AddAddressResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAddressController {
    private OnlineShoppingApi.AddAddressCallBack addAddressCallBack;

    public AddAddressController(OnlineShoppingApi.AddAddressCallBack addAddressCallBack) {
        this.addAddressCallBack = addAddressCallBack;
    }

    public void start(String accessToken, final AddAddress addAddress) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<AddAddressResponse> call = onlineShoppingApi.addAddress(accessToken, addAddress);
        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                addAddressCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                addAddressCallBack.onFailure(t.toString());
            }
        });
    }
}
