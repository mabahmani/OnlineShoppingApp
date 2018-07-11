package com.mab.onlineshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mab.onlineshopping.Data.CheckoutController;
import com.mab.onlineshopping.Data.GetCartProductsController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.CartProduct;
import com.mab.onlineshopping.Model.CartProductsResponse;
import com.mab.onlineshopping.Model.Checkout;
import com.mab.onlineshopping.Model.CheckoutProduct;
import com.mab.onlineshopping.Model.UserUsername;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class CheckoutActivity extends AppCompatActivity {
    private Button checkoutBtn;

    private Checkout checkout = new Checkout();
    private int totalPrice;
    private String addressId;
    private List<CheckoutProduct> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getCartProductsInfo();
        findViews();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineShoppingApi.CheckoutCallBack checkoutCallBack = new OnlineShoppingApi.CheckoutCallBack() {
                    @Override
                    public void onResponse(ResponseBody responseBody) {
                        Toast.makeText(getApplicationContext(),"ثبت شد!",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String cause) {

                    }
                };

                CheckoutController checkoutController = new CheckoutController(checkoutCallBack);
                checkoutController.start(
                        "bearer "+ UserPreferencesManager.getInstance(getApplicationContext()).getAccessToken(),
                        checkout
                );
            }
        });
    }

    private void getCartProductsInfo() {
        totalPrice = getIntent().getIntExtra("totalPrice",0);
        addressId = getIntent().getStringExtra("addressId");

        OnlineShoppingApi.GetCartProductsCallBack getCartProductsCallBack = new OnlineShoppingApi.GetCartProductsCallBack() {
            @Override
            public void onResponse(CartProductsResponse cartProductsResponse) {
                for (CartProduct cartProduct : cartProductsResponse.getCartProductList()){
                    CheckoutProduct checkoutProduct = new CheckoutProduct();
                    checkoutProduct.setCount(cartProduct.getCount());
                    checkoutProduct.setProductId(cartProduct.getProductId());
                    products.add(checkoutProduct);
                }

                checkout.setAddressId(addressId);
                checkout.setTotalPrice(totalPrice);
                checkout.setUsername(UserPreferencesManager.getInstance(getApplicationContext()).getUsername());
                checkout.setProducts(products);
            }

            @Override
            public void onFailure(String cause) {

            }

        };

        UserUsername userUsername = new UserUsername();
        userUsername.setUsername(UserPreferencesManager.getInstance(getApplicationContext()).getUsername());
        GetCartProductsController getCartProductsController = new GetCartProductsController(getCartProductsCallBack);
        getCartProductsController.start(
                "bearer " + UserPreferencesManager.getInstance(getApplicationContext()).getAccessToken(),
                userUsername
        );
    }

    private void findViews() {
        checkoutBtn = findViewById(R.id.checkout);
    }
}
