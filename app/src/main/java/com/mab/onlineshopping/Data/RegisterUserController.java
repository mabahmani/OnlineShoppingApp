package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.User;
import com.mab.onlineshopping.Model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterUserController {
    private OnlineShoppingApi.RegisterUserCallBack registerUserCallBack;

    public RegisterUserController(OnlineShoppingApi.RegisterUserCallBack registerUserCallBack) {
        this.registerUserCallBack = registerUserCallBack;
    }

    public void start(User user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<UserResponse> call = onlineShoppingApi.userRegister(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                registerUserCallBack.onResponse(true, null, response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                registerUserCallBack.onFailure(t.getCause().toString());
            }
        });
    }
}
