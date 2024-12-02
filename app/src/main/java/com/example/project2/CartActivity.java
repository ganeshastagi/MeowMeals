package com.example.project2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.adapters.CartAdapter;
import com.example.project2.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemChangeListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView subTotalPriceText, gstHstPriceText, totalPriceText;
    private List<CartItem> cartItems;
    private double totalPrice;
    Button checkOutButton, goBackToProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view_cart);
        subTotalPriceText = findViewById(R.id.subtotal_price);
        gstHstPriceText = findViewById(R.id.gst_hst_price);
        totalPriceText = findViewById(R.id.total_price);
        checkOutButton = findViewById(R.id.checkout_button);
        goBackToProducts = findViewById(R.id.go_back_to_products);



        cartItems = CartManager.getInstance().getCartItems();
        adapter = new CartAdapter(this, cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotalPrice();

        checkOutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Your cart is Empty!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        goBackToProducts.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductsActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onQuantityChanged() {
        updateTotalPrice();
    }

    public void onItemRemoved() {
        updateTotalPrice();
    }
    @SuppressLint("DefaultLocale")
    private void updateTotalPrice() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        double gstHst = subtotal * 0.13;
        double total = subtotal + gstHst;

        subTotalPriceText.setText(String.format("Subtotal: $%.2f", subtotal));
        gstHstPriceText.setText(String.format("GST/HST: $%.2f",gstHst));
        totalPriceText.setText(String.format("Total: $%.2f", total));
    }
}