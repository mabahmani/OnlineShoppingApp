package com.mab.onlineshopping.Model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mab.onlineshopping.Data.ChangeProductCountController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.ViewHolder> {

    private List<CartItem> cartItemList;
    private String accessToken;

    public CartProductsAdapter(List<CartItem> cartItemList,String accessToken) {
        this.cartItemList = cartItemList;
        this.accessToken = accessToken;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CartItem product = cartItemList.get(position);
        Picasso.get().load(product.getPhotoUrl()).into(holder.productImg);
        holder.productTitle.setText(product.getName());
        holder.productDesc.setText(product.getDescription());
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        final String[] decimalFormatPrice = {decimalFormat.format(product.getPrice() * product.getCount())};
        holder.productPrice.setText(decimalFormatPrice[0]);
        holder.productCount.setText(String.valueOf(product.getCount()));
        holder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineShoppingApi.ChangeProductCountCallBack changeProductCountCallBack = new OnlineShoppingApi.ChangeProductCountCallBack() {
                    @Override
                    public void onResponse(ChangeProductCountResponse changeProductCountResponse) {
                        product.setCount(product.getCount() + 1);
                        holder.productCount.setText(String.valueOf(product.getCount()));
                        decimalFormatPrice[0] = decimalFormat.format(product.getPrice() * product.getCount());
                        holder.productPrice.setText(decimalFormatPrice[0]);
                    }

                    @Override
                    public void onFailure(String cause) {

                    }
                };
                String url = "https://api.backtory.com/object-storage/classes/Basket/"+product.getCartItemId();
                ProductCount productCount = new ProductCount();
                productCount.setCount(product.getCount() + 1);
                ChangeProductCountController changeProductCountController = new ChangeProductCountController(changeProductCountCallBack);
                changeProductCountController.start(url, accessToken, productCount);
            }
        });
        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineShoppingApi.ChangeProductCountCallBack changeProductCountCallBack = new OnlineShoppingApi.ChangeProductCountCallBack() {
                    @Override
                    public void onResponse(ChangeProductCountResponse changeProductCountResponse) {
                        product.setCount(product.getCount() - 1);
                        holder.productCount.setText(String.valueOf(product.getCount()));
                        decimalFormatPrice[0] = decimalFormat.format(product.getPrice() * product.getCount());
                        holder.productPrice.setText(decimalFormatPrice[0]);
                    }

                    @Override
                    public void onFailure(String cause) {

                    }
                };
                String url = "https://api.backtory.com/object-storage/classes/Basket/"+product.getCartItemId();
                ProductCount productCount = new ProductCount();
                productCount.setCount(product.getCount() - 1);
                ChangeProductCountController changeProductCountController = new ChangeProductCountController(changeProductCountCallBack);
                changeProductCountController.start(url, accessToken, productCount);
            }

        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImg;
        public ImageView addProduct;
        public ImageView removeProduct;
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
            addProduct = itemView.findViewById(R.id.add);
            removeProduct = itemView.findViewById(R.id.remove);
        }
    }
}
