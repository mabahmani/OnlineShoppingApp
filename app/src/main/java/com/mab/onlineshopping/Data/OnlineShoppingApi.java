package com.mab.onlineshopping.Data;

import com.mab.onlineshopping.Model.AddAddress;
import com.mab.onlineshopping.Model.AddAddressResponse;
import com.mab.onlineshopping.Model.AddProduct;
import com.mab.onlineshopping.Model.AddToCartResponse;
import com.mab.onlineshopping.Model.AddressResponse;
import com.mab.onlineshopping.Model.CartProductsResponse;
import com.mab.onlineshopping.Model.ChangeProductCountResponse;
import com.mab.onlineshopping.Model.Checkout;
import com.mab.onlineshopping.Model.OrdersResponse;
import com.mab.onlineshopping.Model.ProductCount;
import com.mab.onlineshopping.Model.ProductId;
import com.mab.onlineshopping.Model.ProductsResponse;
import com.mab.onlineshopping.Model.TokenResponse;
import com.mab.onlineshopping.Model.User;
import com.mab.onlineshopping.Model.UserResponse;
import com.mab.onlineshopping.Model.UserUsername;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

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


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @POST("object-storage/classes/query/Basket")
    Call<CartProductsResponse> getCartProducts(
            @Header("Authorization") String accessToken,
            @Body UserUsername userUsername
            );


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @PUT
    Call<ChangeProductCountResponse> changeProductsCount(
            @Url String url,
            @Header("Authorization") String accessToken,
            @Body ProductCount productCount
            );


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @DELETE
    Call<ResponseBody> deleteItemFromCart(
            @Url String url,
            @Header("Authorization") String accessToken
            );


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @POST("object-storage/classes/Address")
    Call<AddAddressResponse> addAddress(
            @Header("Authorization") String accessToken,
            @Body AddAddress addAddress
            );


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @POST("object-storage/classes/query/Address")
    Call<AddressResponse> getAddresses(
            @Header("Authorization") String accessToken,
            @Body UserUsername userUsername
            );

    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @DELETE
    Call<ResponseBody> deleteAddress(
            @Url String url,
            @Header("Authorization") String accessToken
            );


    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @POST("object-storage/classes/Order")
    Call<ResponseBody> checkout(
            @Header("Authorization") String accessToken,
            @Body Checkout checkout
            );

    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @DELETE("object-storage/classes/Basket")
    Call<ResponseBody> deleteAllCartItems(
            @Header("Authorization") String accessToken
            );

    @Headers("X-Backtory-Object-Storage-Id:5a154d2fe4b03ffa0436a535")
    @POST("object-storage/classes/query/Order")
    Call<OrdersResponse> getOrdersHistory(
            @Header("Authorization") String accessToken,
            @Body UserUsername userUsername
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

    interface GetCartProductsCallBack{
        void onResponse(CartProductsResponse cartProductsResponse);
        void onFailure(String cause);
    }

    interface ChangeProductCountCallBack{
        void onResponse(ChangeProductCountResponse changeProductCountResponse);
        void onFailure(String cause);
    }

    interface DeleteItemFromCartCallBack{
        void onResponse(ResponseBody responseBody);
        void onFailure(String cause);
    }

    interface AddAddressCallBack{
        void onResponse(AddAddressResponse addAddressResponse);
        void onFailure(String cause);
    }

    interface GetAddressesCallBack{
        void onResponse(AddressResponse addressResponse);
        void onFailure(String cause);
    }

    interface DeleteAddressCallBack{
        void onResponse(ResponseBody responseBody);
        void onFailure(String cause);
    }

    interface CheckoutCallBack{
        void onResponse(ResponseBody responseBody);
        void onFailure(String cause);
    }

    interface DeleteAllCartItemsCallBack{
        void onResponse(ResponseBody responseBody);
        void onFailure(String cause);
    }

    interface GetOrdersHistoryCallBack{
        void onResponse(OrdersResponse ordersResponse);
        void onFailure(String cause);
    }
}
