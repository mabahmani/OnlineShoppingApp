package com.mab.onlineshopping.Data;

import android.support.annotation.NonNull;

import com.mab.onlineshopping.Model.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUserController {
    private OnlineShoppingApi.LoginUserCallBack loginUserCallBack;

    public LoginUserController(OnlineShoppingApi.LoginUserCallBack loginUserCallBack) {
        this.loginUserCallBack = loginUserCallBack;
    }

    public void start(String username, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShoppingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShoppingApi onlineShoppingApi = retrofit.create(OnlineShoppingApi.class);
        Call<TokenResponse> call = onlineShoppingApi.userLogin(username,password);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<TokenResponse> call, @NonNull Response<TokenResponse> response) {
                if(response.isSuccessful()){
                    loginUserCallBack.onResponse(true,null,response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TokenResponse> call, @NonNull Throwable t) {
                loginUserCallBack.onFailure(t.toString());
            }
        });
    }
}
