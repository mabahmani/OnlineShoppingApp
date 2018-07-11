package com.mab.onlineshopping.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mab.onlineshopping.Data.DeleteAddressController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.R;

import java.util.List;

import okhttp3.ResponseBody;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {
    private List<Address> addressList;
    private Context context;

    public AddressesAdapter(List<Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.addressTextView.setText(addressList.get(position).getDetail());
        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineShoppingApi.DeleteAddressCallBack deleteAddressCallBack = new OnlineShoppingApi.DeleteAddressCallBack() {
                    @Override
                    public void onResponse(ResponseBody responseBody) {
                        addressList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String cause) {

                    }
                };

                String url = "object-storage/classes/Address/" + addressList.get(position).getId();
                DeleteAddressController deleteAddressController = new DeleteAddressController(deleteAddressCallBack);
                deleteAddressController.start(
                        "bearer " + UserPreferencesManager.getInstance(context).getAccessToken(),
                        url
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView addressTextView;
        public ImageView deleteAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            addressTextView = itemView.findViewById(R.id.address);
            deleteAddress = itemView.findViewById(R.id.address_remove);
        }
    }
}
