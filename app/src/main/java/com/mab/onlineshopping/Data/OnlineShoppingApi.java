package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.AddProduct;
import com.mab.onlineshopping.Model.AddToCartResponse;
import com.mab.onlineshopping.Model.Product;
import com.mab.onlineshopping.Model.ProductId;
import com.mab.onlineshopping.Model.ProductsResponse;
import com.mab.onlineshopping.Model.TokenResponse;
import com.mab.onlineshopping.Model.User;
import com.mab.onlineshopping.Model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OnlineShoppingApi {
    String BASE_URL = "https://api.backtory.com/";

    @Headers({
            "X-Backtory-Authentication-Id:5a154d2fe4b03ffa0436a534",
            "X-Backtory-Authentication-Key:57bdf2629df04f46a31de972"})
    @FormUrlEncoded
    @POST("auth/login")
    Call<TokenResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password);


    @Headers("X-Backtory-Authentication-Id:5a154d2fe4b03ffa0436a534")
    @POST("auth/users")
    Call<UserResponse> userRegister(@Body User user);


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @POST("object-storage/classes/query/Product")
    Call<ProductsResponse> getProducts(
            @Header("Authorization") String accessToken
    );


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @POST("object-storage/classes/query/Product")
    Call<ProductsResponse> getProductInfo(
            @Header("Authorization") String accessToken,
            @Body ProductId productId
            );


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @POST("object-storage/classes/Basket")
    Call<AddToCartResponse> addProductToCart(
            @Header("Authorization") String accessToken,
            @Body AddProduct addProduct
            );


    interface LoginUserCallBack{
        void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse );
        void onFailure(String cause);
    }

    interface RegisterUserCallBack{
        void onResponse(boolean successful, String errorDescription, UserResponse userResponse );
        void onFailure(String cause);
    }

    interface GetProductsCallBack{
        void onResponse(ProductsResponse productsResponse);
        void onFailure(String cause);
    }

    interface GetProductInfoCallBack{
        void onResponse(ProductsResponse productsResponse);
        void onFailure(String cause);
    }

    interface AddProductToCartCallBack{
        void onResponse(AddToCartResponse addToCartResponse);
        void onFailure(String cause);
    }
}
