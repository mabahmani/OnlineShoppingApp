package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.Checkout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutController {
    private OnlineShoppingApi.CheckoutCallBack checkoutCallBack;

    public CheckoutController(OnlineShoppingApi.CheckoutCallBack checkoutCallBack) {
        this.checkoutCallBack = checkoutCallBack;
    }

    public void start(String accessToken, Checkout checkout) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<ResponseBody> call = onlineShoppingApi.checkout(accessToken, checkout);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                checkoutCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                checkoutCallBack.onFailure(t.toString());
            }
        });
    }
}
