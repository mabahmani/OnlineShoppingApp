package com.mab.onlineshopping.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mab.onlineshopping.Data.ChangeProductCountController;
import com.mab.onlineshopping.Data.DeleteItemFromCartController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.ResponseBody;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.ViewHolder> {

    private List<CartItem> cartItemList;
    private Context context;

    public CartProductsAdapter(List<CartItem> cartItemList,Context context) {
        this.cartItemList = cartItemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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
                changeProductCountController.start(url, "bearer " + UserPreferencesManager.getInstance(context).getAccessToken(), productCount);
            }
        });
        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getCount() == 1) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case DialogInterface.BUTTON_POSITIVE: {
                                    OnlineShoppingApi.DeleteItemFromCartCallBack deleteItemFromCartCallBack = new OnlineShoppingApi.DeleteItemFromCartCallBack() {
                                        @Override
                                        public void onResponse(ResponseBody responseBody) {
                                            Toast.makeText(context,"محصول با موفقیت حذف شد!",Toast.LENGTH_LONG).show();
                                            cartItemList.remove(position);
                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFailure(String cause) {

                                        }
                                    };
                                    String url = "https://api.backtory.com/object-storage/classes/Basket/"+product.getCartItemId();
                                    DeleteItemFromCartController deleteItemFromCartController = new DeleteItemFromCartController(deleteItemFromCartCallBack);
                                    deleteItemFromCartController.start(
                                            url,
                                            "bearer " + UserPreferencesManager.getInstance(context).getAccessToken()
                                            );
                                    break;
                                }
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("این ایتم حذف شود؟")
                            .setPositiveButton("اره",dialogClickListener)
                            .setNegativeButton("بی خیال",dialogClickListener).show();
                }
                else {
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
                    String url = "https://api.backtory.com/object-storage/classes/Basket/" + product.getCartItemId();
                    ProductCount productCount = new ProductCount();
                    productCount.setCount(product.getCount() - 1);
                    ChangeProductCountController changeProductCountController = new ChangeProductCountController(changeProductCountCallBack);
                    changeProductCountController.start(url, "bearer " + UserPreferencesManager.getInstance(context).getAccessToken(), productCount);
                }
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
