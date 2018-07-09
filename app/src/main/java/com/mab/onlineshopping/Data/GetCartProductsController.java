package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.CartProductsResponse;
import com.mab.onlineshopping.Model.UserUsername;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetCartProductsController {
    private OnlineShoppingApi.GetCartProductsCallBack getCartProductsCallBack;

    public GetCartProductsController(OnlineShoppingApi.GetCartProductsCallBack getCartProductsCallBack) {
        this.getCartProductsCallBack = getCartProductsCallBack;
    }

    public void start(String accessToken, UserUsername userUsername) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<CartProductsResponse> call = onlineShoppingApi.getCartProducts(accessToken, userUsername);
        call.enqueue(new Callback<CartProductsResponse>() {
            @Override
            public void onResponse(Call<CartProductsResponse> call, Response<CartProductsResponse> response) {
                if (response.isSuccessful())
                    getCartProductsCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<CartProductsResponse> call, Throwable t) {
                getCartProductsCallBack.onFailure(t.getCause().toString());
            }
        });
    }
}
