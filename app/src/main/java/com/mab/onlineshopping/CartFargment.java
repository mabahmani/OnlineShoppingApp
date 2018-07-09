package com.mab.onlineshopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mab.onlineshopping.Data.GetCartProductsController;
import com.mab.onlineshopping.Data.GetProductInfoController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.CartItem;
import com.mab.onlineshopping.Model.CartProduct;
import com.mab.onlineshopping.Model.CartProductsAdapter;
import com.mab.onlineshopping.Model.CartProductsResponse;
import com.mab.onlineshopping.Model.ProductId;
import com.mab.onlineshopping.Model.ProductsResponse;
import com.mab.onlineshopping.Model.UserUsername;

import java.util.ArrayList;
import java.util.List;

public class CartFargment extends Fragment {
    private RecyclerView recyclerView;
    private CartProductsAdapter cartProductsAdapter;
    private List<CartItem> cartItemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        initialCartList();

        OnlineShoppingApi.GetCartProductsCallBack getCartProductsCallBack = new OnlineShoppingApi.GetCartProductsCallBack() {
            @Override
            public void onResponse(CartProductsResponse cartProductsResponse) {
                for (final CartProduct cartProduct : cartProductsResponse.getCartProductList()){
                    OnlineShoppingApi.GetProductInfoCallBack getProductInfoCallBack = new OnlineShoppingApi.GetProductInfoCallBack() {
                        @Override
                        public void onResponse(ProductsResponse productsResponse) {
                            CartItem cartItem = new CartItem();
                            cartItem.setDescription(productsResponse.getProducts().get(0).getDescription());
                            cartItem.setName(productsResponse.getProducts().get(0).getName());
                            cartItem.setPhotoUrl(productsResponse.getProducts().get(0).getPhotoUrl());
                            cartItem.setPrice(productsResponse.getProducts().get(0).getPrice());
                            cartItem.setCount(cartProduct.getCount());
                            cartItem.setCartItemId(cartProduct.getId());
                            cartItemList.add(cartItem);
                            cartProductsAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(String cause) {

                        }
                    };

                    ProductId productId = new ProductId();
                    productId.setId(cartProduct.getProductId());
                    GetProductInfoController getProductInfoController = new GetProductInfoController(getProductInfoCallBack);
                    getProductInfoController.start(
                            "bearer " + UserPreferencesManager.getInstance(getActivity()).getAccessToken(),
                            productId
                    );
                }

            }

            @Override
            public void onFailure(String cause) {

            }
        };

        UserUsername userUsername = new UserUsername();
        userUsername.setUsername(UserPreferencesManager.getInstance(getActivity()).getUsername());
        GetCartProductsController getCartProductsController = new GetCartProductsController(getCartProductsCallBack);
        getCartProductsController.start(
                "bearer " + UserPreferencesManager.getInstance(getActivity()).getAccessToken(),
                userUsername
        );
    }

    private void initialCartList() {
        cartProductsAdapter = new CartProductsAdapter(
                cartItemList, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(cartProductsAdapter);
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.cart_list);
    }
}
