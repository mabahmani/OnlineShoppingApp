package com.mab.onlineshopping.Model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mab.onlineshopping.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.ViewHolder> {

    private List<CartItem> cartItemList;

    public CartProductsAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem product = cartItemList.get(position);
        Picasso.get().load(product.getPhotoUrl()).into(holder.productImg);
        holder.productTitle.setText(product.getName());
        holder.productDesc.setText(product.getDescription());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String decimalFormatPrice = decimalFormat.format(product.getPrice());
        holder.productPrice.setText(decimalFormatPrice);
        holder.productCount.setText(String.valueOf(product.getCount()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImg;
        public TextView productTitle;
        public TextView productDesc;
        public TextView productPrice;
        public TextView productCount;

        public ViewHolder(View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.product_img);
            productTitle = itemView.findViewById(R.id.product_title);
            productDesc = itemView.findViewById(R.id.product_desc);
            productPrice = itemView.findViewById(R.id.product_price);
            productCount = itemView.findViewById(R.id.count);
        }
    }
}
