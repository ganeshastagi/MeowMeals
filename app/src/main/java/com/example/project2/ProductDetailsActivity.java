package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView productName, productDescription, productPrice;
    ImageView productImage;
    ImageView increaseQTY, decreaseQTY;
    EditText quantityInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_desc);
        productPrice = findViewById(R.id.product_price);
        productImage = findViewById(R.id.product_img);

        increaseQTY = findViewById(R.id.increase_quantity);
        decreaseQTY = findViewById(R.id.decrease_quantity);
        quantityInput = findViewById(R.id.quantity_input);

        Intent intent = getIntent();
        String name = intent.getStringExtra("product_name");
        String description = intent.getStringExtra("product_desc");
        String imageURL = intent.getStringExtra("product_img");
        double price = intent.getDoubleExtra("product_price", 0.0);

        productName.setText(name);
        productDescription.setText(description);
        productPrice.setText(String.format("$%.2f", price));
        Glide.with(this).load(imageURL).into(productImage);

        increaseQTY.setOnClickListener(v -> updateQuantity(1));
        decreaseQTY.setOnClickListener(v -> updateQuantity(-1));

        findViewById(R.id.go_to_cart_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateQuantity(int i) {
        String currentQuantityText = quantityInput.getText().toString();
        int currentQuantity = 1;

        try {
            currentQuantity = Integer.parseInt(currentQuantityText);
        } catch (NumberFormatException e) {
            currentQuantity = 1;
        }
        currentQuantity += i;
        if (currentQuantity <1) currentQuantity = 1;
        quantityInput.setText(String.valueOf(currentQuantity));
    }
}