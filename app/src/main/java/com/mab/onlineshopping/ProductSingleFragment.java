package com.mab.onlineshopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mab.onlineshopping.Data.GetProductInfoController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Model.Product;
import com.mab.onlineshopping.Model.ProductId;
import com.mab.onlineshopping.Model.ProductsResponse;
import com.squareup.picasso.Picasso;

public class ProductSingleFragment extends Fragment {

    private ImageView   productImg;
    private TextView    productTitle;
    private TextView    productPrice;
    private TextView    productDesc;
    private Button      addToCart;

    private ProductId productId;
    private String accessToken;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Gson gson = new Gson();
        productId = gson.fromJson(getArguments().getString("productId",null),ProductId.class);
        accessToken = "bearer " + getArguments().getString("accessToken",null);
        return inflater.inflate(R.layout.fragment_product_single,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        OnlineShoppingApi.GetProductInfoCallBack getProductInfoCallBack = new OnlineShoppingApi.GetProductInfoCallBack() {
            @Override
            public void onResponse(ProductsResponse productsResponse) {
                bindViews(productsResponse.getProducts().get(0));
            }

            @Override
            public void onFailure(String cause) {

            }
        };

        GetProductInfoController getProductInfoController = new GetProductInfoController(getProductInfoCallBack);
        getProductInfoController.start(accessToken,productId);
    }

    private void bindViews(Product product) {
        Picasso.get().load(product.getPhotoUrl()).into(productImg);
        productTitle.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()));
        productDesc.setText(product.getDescription());
    }

    private void findViews(View view) {
        productImg = view.findViewById(R.id.product_img);
        productTitle = view.findViewById(R.id.product_title);
        productPrice = view.findViewById(R.id.product_price);
        productDesc = view.findViewById(R.id.product_desc);
        addToCart = view.findViewById(R.id.add_to_cart_btn);
    }
}
