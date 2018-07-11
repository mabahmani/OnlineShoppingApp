package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.UserUsername;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteAllCartItemsController {
    private OnlineShoppingApi.DeleteAllCartItemsCallBack deleteAllCartItemsCallBack;

    public DeleteAllCartItemsController(OnlineShoppingApi.DeleteAllCartItemsCallBack deleteAllCartItemsCallBack) {
        this.deleteAllCartItemsCallBack = deleteAllCartItemsCallBack;
    }

    public void start(String accessToken) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<ResponseBody> call = onlineShoppingApi.deleteAllCartItems(accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                deleteAllCartItemsCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                deleteAllCartItemsCallBack.onFailure(t.toString());
            }
        });
    }
}
