package com.example.project2.adapters;

import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.CartManager;
import com.example.project2.ProductDetailsActivity;
import com.example.project2.R;
import com.example.project2.models.CartItem;
import com.example.project2.models.Products;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Products> productsList;

    public ProductsAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductViewHolder holder, int position) {
        Products products = productsList.get(position);
        holder.name.setText(products.getName());
        holder.description.setText(products.getDescription());
        holder.price.setText(format("$%.2f", products.getPrice()));
        Glide.with(context).load(products.getImageURL()).into(holder.image);

        holder.addToCart.setOnClickListener(v -> {
            CartItem cartItem = new CartItem(products.getName(), products.getPrice(), 1, products.getImageURL());
            CartManager.getInstance().addToCart(cartItem);
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product_name", products.getName());
                intent.putExtra("product_desc", products.getDescription());
                intent.putExtra("product_price", products.getPrice());
                intent.putExtra("product_img", products.getImageURL());
                intent.putExtra("product_fullDesc", products.getFullDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price;
        ImageView image;
        Button addToCart;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_desc);
            price = itemView.findViewById(R.id.product_price);
            image = itemView.findViewById(R.id.product_img);
            addToCart = itemView.findViewById(R.id.add_to_cart_button);
        }
    }
}
