package com.mab.onlineshopping.Model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mab.onlineshopping.R;

import java.util.List;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {
    private List<Address> addressList;

    public AddressesAdapter(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.addressTextView.setText(addressList.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView addressTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            addressTextView = itemView.findViewById(R.id.address);
        }
    }
}
