package com.mab.onlineshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.mab.onlineshopping.Data.GetProductsController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.Product;
import com.mab.onlineshopping.Model.ProductsAdapter;
import com.mab.onlineshopping.Model.ProductsResponse;

import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        findViews();

        toolbar.setTitle("فروشگاه آنلاین");
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        OnlineShoppingApi.GetProductsCallBack getProductsCallBack = new OnlineShoppingApi.GetProductsCallBack() {
            @Override
            public void onResponse(ProductsResponse productsResponse) {
                initialProductsList(productsResponse);
            }

            @Override
            public void onFailure(String cause) {
                Toast.makeText(getApplicationContext(),cause,Toast.LENGTH_LONG).show();
            }
        };

        GetProductsController getProductsController = new GetProductsController(getProductsCallBack);
        getProductsController.start("bearer " + UserPreferencesManager.getInstance(getApplicationContext()).getAccessToken());
    }

    private void initialProductsList(ProductsResponse productsResponse) {
        productsAdapter = new ProductsAdapter(productsResponse.getProducts());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(productsAdapter);
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.product_list);
    }
}
