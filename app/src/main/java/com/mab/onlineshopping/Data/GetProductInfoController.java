package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.ProductId;
import com.mab.onlineshopping.Model.ProductsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetProductInfoController {
    private OnlineShoppingApi.GetProductInfoCallBack getProductInfoCallBack;

    public GetProductInfoController(OnlineShoppingApi.GetProductInfoCallBack getProductInfoCallBack) {
        this.getProductInfoCallBack = getProductInfoCallBack;
    }

    public void start(String accessToken, ProductId productId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<ProductsResponse> call = onlineShoppingApi.getProductInfo(accessToken, productId);
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                getProductInfoCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                getProductInfoCallBack.onFailure(t.toString());
            }
        });
    }
}
