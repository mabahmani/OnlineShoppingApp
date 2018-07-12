package com.mab.onlineshopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mab.onlineshopping.Data.AddProductToCartController;
import com.mab.onlineshopping.Data.GetProductInfoController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.AddProduct;
import com.mab.onlineshopping.Model.AddToCartResponse;
import com.mab.onlineshopping.Model.Product;
import com.mab.onlineshopping.Model.ProductId;
import com.mab.onlineshopping.Model.ProductsResponse;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductSingleFragment extends Fragment {

    private ImageView   productImg;
    private TextView    productTitle;
    private TextView    productPrice;
    private TextView    productDesc;
    private TextView    toman;
    private Button      addToCart;
    private ProgressBar progressBar;

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

        progressBar.setVisibility(View.VISIBLE);
        toman.setVisibility(View.INVISIBLE);
        addToCart.setVisibility(View.INVISIBLE);

        OnlineShoppingApi.GetProductInfoCallBack getProductInfoCallBack = new OnlineShoppingApi.GetProductInfoCallBack() {
            @Override
            public void onResponse(ProductsResponse productsResponse) {
                progressBar.setVisibility(View.INVISIBLE);
                toman.setVisibility(View.VISIBLE);
                addToCart.setVisibility(View.VISIBLE);
                bindViews(productsResponse.getProducts().get(0));
            }

            @Override
            public void onFailure(String cause) {

            }
        };

        GetProductInfoController getProductInfoController = new GetProductInfoController(getProductInfoCallBack);
        getProductInfoController.start(accessToken,productId);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineShoppingApi.AddProductToCartCallBack addProductToCartCallBack = new OnlineShoppingApi.AddProductToCartCallBack() {
                    @Override
                    public void onResponse(AddToCartResponse addToCartResponse) {
                        Toast.makeText(getActivity(),"محصول با موفقیت به سبد اضافه شد!",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String cause) {

                    }
                };

                AddProduct addProduct = new AddProduct();
                addProduct.setCount(1);
                addProduct.setProductId(productId.getId());
                addProduct.setUsername(UserPreferencesManager.getInstance(getActivity()).getUsername());
                AddProductToCartController addProductToCartController = new AddProductToCartController(addProductToCartCallBack);
                addProductToCartController.start(accessToken,addProduct);
            }
        });
    }

    private void bindViews(Product product) {
        Picasso.get().load(product.getPhotoUrl()).into(productImg);
        productTitle.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String formattedPrice = decimalFormat.format(product.getPrice());
        productPrice.setText(formattedPrice);
        productDesc.setText(product.getDescription());
    }

    private void findViews(View view) {
        productImg = view.findViewById(R.id.product_img);
        productTitle = view.findViewById(R.id.product_title);
        productPrice = view.findViewById(R.id.product_price);
        productDesc = view.findViewById(R.id.product_desc);
        addToCart = view.findViewById(R.id.add_to_cart_btn);
        progressBar = view.findViewById(R.id.progress_bar);
        toman = view.findViewById(R.id.toman);
    }
}
