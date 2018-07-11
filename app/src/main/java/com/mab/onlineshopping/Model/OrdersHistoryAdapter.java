package com.mab.onlineshopping.Model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mab.onlineshopping.R;

import java.text.DecimalFormat;
import java.util.List;

public class OrdersHistoryAdapter extends RecyclerView.Adapter<OrdersHistoryAdapter.ViewHolder> {
    private List<Order> orderList;

    public OrdersHistoryAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String decimalFormatPrice = decimalFormat.format(orderList.get(position).getTotalPrice());
        holder.orderPrice.setText(decimalFormatPrice);
        holder.orderId.setText(orderList.get(position).getId());
        holder.orderDate.setText(orderList.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderId;
        public TextView orderDate;
        public TextView orderPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.order_date);
            orderId = itemView.findViewById(R.id.order_id);
            orderPrice = itemView.findViewById(R.id.order_price);
        }
    }
}
