package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.OrdersResponse;
import com.mab.onlineshopping.Model.UserUsername;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetOrdersHistoryController {
    private OnlineShoppingApi.GetOrdersHistoryCallBack getOrdersHistoryCallBack;

    public GetOrdersHistoryController(OnlineShoppingApi.GetOrdersHistoryCallBack getOrdersHistoryCallBack) {
        this.getOrdersHistoryCallBack = getOrdersHistoryCallBack;
    }

    public void start(String accessToken, UserUsername userUsername) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<OrdersResponse> call = onlineShoppingApi.getOrdersHistory(accessToken, userUsername);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                getOrdersHistoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                getOrdersHistoryCallBack.onFailure(t.toString());
            }

        });
    }
}
