package com.mab.onlineshopping.Data;

import android.support.annotation.NonNull;

import com.mab.onlineshopping.Model.ProductsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetProductsController {
    private OnlineShoppingApi.GetProductsCallBack getProductsCallBack;

    public GetProductsController(OnlineShoppingApi.GetProductsCallBack getProductsCallBack) {
        this.getProductsCallBack = getProductsCallBack;
    }

    public void start(String accessToken) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<ProductsResponse> call = onlineShoppingApi.getProducts(accessToken);
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductsResponse> call, @NonNull Response<ProductsResponse> response) {
                if (response.isSuccessful()) {
                    getProductsCallBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductsResponse> call, @NonNull Throwable t) {
                getProductsCallBack.onFailure(t.toString());
            }
        });
    }
}
