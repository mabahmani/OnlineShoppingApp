package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.ChangeProductCountResponse;
import com.mab.onlineshopping.Model.ProductCount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeProductCountController {
    private OnlineShoppingApi.ChangeProductCountCallBack changeProductCountCallBack;

    public ChangeProductCountController(OnlineShoppingApi.ChangeProductCountCallBack changeProductCountCallBack) {
        this.changeProductCountCallBack = changeProductCountCallBack;
    }

    public void start(String url, String accessToken, ProductCount productCount) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<ChangeProductCountResponse> call = onlineShoppingApi.changeProductsCount(url, accessToken, productCount);
        call.enqueue(new Callback<ChangeProductCountResponse>() {
            @Override
            public void onResponse(Call<ChangeProductCountResponse> call, Response<ChangeProductCountResponse> response){
                changeProductCountCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ChangeProductCountResponse> call, Throwable t) {
                changeProductCountCallBack.onFailure(t.toString());
            }
        });
    }
}
