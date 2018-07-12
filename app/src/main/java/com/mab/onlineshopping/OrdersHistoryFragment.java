package com.mab.onlineshopping;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mab.onlineshopping.Data.GetOrdersHistoryController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.OrdersHistoryAdapter;
import com.mab.onlineshopping.Model.OrdersResponse;
import com.mab.onlineshopping.Model.UserUsername;

public class OrdersHistoryFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private OrdersHistoryAdapter ordersHistoryAdapter;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders_history,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        progressBar.setVisibility(View.VISIBLE);

        OnlineShoppingApi.GetOrdersHistoryCallBack getOrdersHistoryCallBack = new OnlineShoppingApi.GetOrdersHistoryCallBack() {
            @Override
            public void onResponse(OrdersResponse ordersResponse) {
                progressBar.setVisibility(View.INVISIBLE);
                ordersHistoryAdapter = new OrdersHistoryAdapter(ordersResponse.getOrderList());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(ordersHistoryAdapter);
            }

            @Override
            public void onFailure(String cause) {

            }
        };

        UserUsername userUsername = new UserUsername();
        userUsername.setUsername(UserPreferencesManager.getInstance(getActivity()).getUsername());
        GetOrdersHistoryController getOrdersHistoryController = new GetOrdersHistoryController(getOrdersHistoryCallBack);
        getOrdersHistoryController.start(
                "bearer " + UserPreferencesManager.getInstance(getActivity()).getAccessToken(),
                userUsername
        );
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.history_list);
        progressBar = view.findViewById(R.id.progress_bar);
    }
}
