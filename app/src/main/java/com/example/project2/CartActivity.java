package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView totalPriceText;
    private List<CartItem> cartItems;
    private double totalPrice;
    Button checkOutButton;

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
        totalPriceText = findViewById(R.id.total_price);
        checkOutButton = findViewById(R.id.checkout_button);

        cartItems = new ArrayList<>();
        //This should come from database

        adapter = new CartAdapter(this, cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotalPrice();

        checkOutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
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
    private void updateTotalPrice() {
        totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();

        }
        totalPrice += totalPrice * 0.13;
        totalPriceText.setText(String.format("Total: $%.2f", totalPrice));
    }
}