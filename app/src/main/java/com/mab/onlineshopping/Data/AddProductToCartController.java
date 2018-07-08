package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.AddProduct;
import com.mab.onlineshopping.Model.AddToCartResponse;
import com.mab.onlineshopping.Model.ProductId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProductToCartController {
    private OnlineShoppingApi.AddProductToCartCallBack addProductToCartCallBack;

    public AddProductToCartController(OnlineShoppingApi.AddProductToCartCallBack addProductToCartCallBack) {
        this.addProductToCartCallBack = addProductToCartCallBack;
    }

    public void start(String accessToken, AddProduct addProduct) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<AddToCartResponse> call = onlineShoppingApi.addProductToCart(accessToken, addProduct);
        call.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                addProductToCartCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                addProductToCartCallBack.onFailure(t.toString());
            }
        });
    }
}
