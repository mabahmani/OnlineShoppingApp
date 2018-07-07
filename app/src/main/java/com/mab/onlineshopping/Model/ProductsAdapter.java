package com.mab.onlineshopping.Model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mab.onlineshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> productList;

    public ProductsAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);
        Picasso.get().load(product.getPhotoUrl()).into(holder.productImg);
        holder.rate.setText(String.valueOf(product.getRating()));
        holder.rateCount.setText(String.valueOf(product.getRatingCount()));
        holder.productTitle.setText(product.getName());
        holder.productDesc.setText(product.getDescription());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImg;
        public TextView rate;
        public TextView rateCount;
        public TextView productTitle;
        public TextView productDesc;
        public TextView productPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.product_img);
            rate = itemView.findViewById(R.id.rate);
            rateCount = itemView.findViewById(R.id.rate_count);
            productTitle = itemView.findViewById(R.id.product_title);
            productDesc = itemView.findViewById(R.id.product_desc);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
