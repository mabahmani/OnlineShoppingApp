package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.ChangeProductCountResponse;
import com.mab.onlineshopping.Model.ProductCount;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteItemFromCartController {
    private OnlineShoppingApi.DeleteItemFromCartCallBack deleteItemFromCartCallBack;

    public DeleteItemFromCartController(OnlineShoppingApi.DeleteItemFromCartCallBack deleteItemFromCartCallBack) {
        this.deleteItemFromCartCallBack = deleteItemFromCartCallBack;
    }

    public void start(String url, String accessToken) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<ResponseBody> call = onlineShoppingApi.deleteItemFromCart(url, accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                deleteItemFromCartCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                deleteItemFromCartCallBack.onFailure(t.toString());
            }
        });

    }
}
