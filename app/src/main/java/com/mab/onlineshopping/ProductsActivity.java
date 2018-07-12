package com.mab.onlineshopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mab.onlineshopping.Data.DeleteItemFromCartController;
import com.mab.onlineshopping.Data.GetProductsController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.ProductId;
import com.mab.onlineshopping.Model.ProductsAdapter;
import com.mab.onlineshopping.Model.ProductsResponse;
import com.mab.onlineshopping.Model.RecyclerTouchListener;

import okhttp3.ResponseBody;

public class ProductsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        findViews();

        progressBar.setVisibility(View.VISIBLE);

        toolbar.setTitle("فروشگاه آنلاین");
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.shopping_cart){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame,new CartFargment(),null)
                            .addToBackStack(null)
                            .commit();
                 }
                 else if(item.getItemId() == R.id.shopping_history){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame,new OrdersHistoryFragment(),null)
                            .addToBackStack(null)
                            .commit();
                }

                else if(item.getItemId() == R.id.user_logout){
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case DialogInterface.BUTTON_POSITIVE: {
                                    UserPreferencesManager.getInstance(getApplicationContext()).clear();
                                    Intent intent = new Intent(ProductsActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    break;
                                }
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductsActivity.this);
                    builder.setMessage("میخواهید از حساب کاربری خود خارج شوید؟")
                            .setPositiveButton("اره",dialogClickListener)
                            .setNegativeButton("بی خیال",dialogClickListener).show();
                }
                return false;
            }
        });

        OnlineShoppingApi.GetProductsCallBack getProductsCallBack = new OnlineShoppingApi.GetProductsCallBack() {
            @Override
            public void onResponse(ProductsResponse productsResponse) {
                progressBar.setVisibility(View.INVISIBLE);
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

    private void initialProductsList(final ProductsResponse productsResponse) {
        productsAdapter = new ProductsAdapter(productsResponse.getProducts());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ProductId productId = new ProductId();
                productId.setId(productsResponse.getProducts().get(position).getId());

                Gson gson = new Gson();
                String productIdToJson = gson.toJson(productId);


                Bundle bundle = new Bundle();
                bundle.putString("productId",productIdToJson);
                bundle.putString("accessToken", UserPreferencesManager.getInstance(getApplicationContext()).getAccessToken());

                ProductSingleFragment productSingleFragment = new ProductSingleFragment();
                productSingleFragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,productSingleFragment,null)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(productsAdapter);
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.product_list);
        progressBar = findViewById(R.id.progress_bar);
    }
}
