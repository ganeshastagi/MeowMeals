package com.example.project2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.R;
import com.example.project2.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final List<CartItem> cartItems;
    private final OnCartItemChangeListener listener;


    public CartAdapter(Context context, List<CartItem> cartItems, OnCartItemChangeListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.productName.setText(item.getName());
        holder.productPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.productQuantity.setText(String.valueOf(item.getQuantity()));
        Glide.with(context).load(item.getImageURL()).into(holder.image);

        holder.increaseQuantity.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            listener.onQuantityChanged();
            notifyItemChanged(position);
        });

        holder.decreaseQuantity.setOnClickListener(v -> {
            if(item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                listener.onQuantityChanged();
                notifyItemChanged(position);
            }
        });

        holder.removeItem.setOnClickListener(v -> {
            if (position >=0 && position < cartItems.size()) {
                cartItems.remove(position);
                listener.onItemRemoved();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void clearCart() {
        cartItems.clear();
        notifyDataSetChanged();
    }


    public interface OnCartItemChangeListener {
        void onQuantityChanged();
        void onItemRemoved();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        TextView productName, productPrice, productQuantity;
        ImageView increaseQuantity, decreaseQuantity, removeItem;

        Button addToCart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            increaseQuantity = itemView.findViewById(R.id.increase_quantity);
            decreaseQuantity = itemView.findViewById(R.id.decrease_quantity);
            removeItem = itemView.findViewById(R.id.remove_item);
            image = itemView.findViewById(R.id.product_img);
            addToCart = itemView.findViewById(R.id.add_to_cart_button);
        }
    }
}
