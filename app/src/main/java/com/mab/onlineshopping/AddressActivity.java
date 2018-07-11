package com.mab.onlineshopping;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.mab.onlineshopping.Data.AddAddressController;
import com.mab.onlineshopping.Data.GetAddressesController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.AddAddress;
import com.mab.onlineshopping.Model.AddAddressResponse;
import com.mab.onlineshopping.Model.Address;
import com.mab.onlineshopping.Model.AddressResponse;
import com.mab.onlineshopping.Model.AddressesAdapter;
import com.mab.onlineshopping.Model.UserUsername;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button addAddress;
    private TextInputEditText addAddressEt;

    private AddressesAdapter addressesAdapter;
    private List<Address> addressList = new ArrayList<>();
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        totalPrice = getIntent().getIntExtra("totalPrice", 0);

        findViews();


         OnlineShoppingApi.GetAddressesCallBack getAddressesCallBack = new OnlineShoppingApi.GetAddressesCallBack() {
            @Override
            public void onResponse(AddressResponse addressResponse) {
                addressesAdapter = new AddressesAdapter(addressList,getApplicationContext(),totalPrice);
                addressesAdapter.notifyDataSetChanged();
                addressList.addAll(addressResponse.getAddressList());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(addressesAdapter);
            }

            @Override
            public void onFailure(String cause) {

            }

         };
         UserUsername userUsername = new UserUsername();
         userUsername.setUsername(UserPreferencesManager.getInstance(getApplicationContext()).getUsername());
         GetAddressesController getAddressesController = new GetAddressesController(getAddressesCallBack);
         getAddressesController.start(
                 "bearer " + UserPreferencesManager.getInstance(getApplicationContext()).getAccessToken(),
                 userUsername
         );



        addAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                OnlineShoppingApi.AddAddressCallBack addAddressCallBack = new OnlineShoppingApi.AddAddressCallBack() {
                    @Override
                    public void onResponse(AddAddressResponse addAddressResponse) {
                        OnlineShoppingApi.GetAddressesCallBack getAddressesCallBack = new OnlineShoppingApi.GetAddressesCallBack() {
                            @Override
                            public void onResponse(AddressResponse addressResponse) {
                                addressList.add(0,addressResponse.getAddressList().get(addressResponse.getAddressList().size()-1));
                                addressesAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(String cause) {

                            }

                        };
                        UserUsername userUsername = new UserUsername();
                        userUsername.setUsername(UserPreferencesManager.getInstance(getApplicationContext()).getUsername());
                        GetAddressesController getAddressesController = new GetAddressesController(getAddressesCallBack);
                        getAddressesController.start(
                                "bearer " + UserPreferencesManager.getInstance(getApplicationContext()).getAccessToken(),
                                userUsername
                        );                    }

                    @Override
                    public void onFailure(String cause) {

                    }
                };

                AddAddress addAddress = new AddAddress();
                addAddress.setDetail(addAddressEt.getText().toString());
                addAddress.setUsername(UserPreferencesManager.getInstance(getApplicationContext()).getUsername());

                AddAddressController addAddressController = new AddAddressController(addAddressCallBack);
                addAddressController.start(
                        "bearer " + UserPreferencesManager.getInstance(getApplicationContext()).getAccessToken(),
                        addAddress
                );
            }
        });
    }


    private void findViews() {
        addAddress = findViewById(R.id.add_new_address);
        addAddressEt = findViewById(R.id.new_address_et);
        recyclerView = findViewById(R.id.address_list);
    }
}
