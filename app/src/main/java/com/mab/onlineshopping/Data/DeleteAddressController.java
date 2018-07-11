package com.mab.onlineshopping.Data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteAddressController {
    private OnlineShoppingApi.DeleteAddressCallBack deleteAddressCallBack;

    public DeleteAddressController(OnlineShoppingApi.DeleteAddressCallBack deleteAddressCallBack) {
        this.deleteAddressCallBack = deleteAddressCallBack;
    }

    public void start(String accessToken, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<ResponseBody> call = onlineShoppingApi.deleteAddress(url, accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                deleteAddressCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                deleteAddressCallBack.onFailure(t.toString());
            }
        });
    }
}
