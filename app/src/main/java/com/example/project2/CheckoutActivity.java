package com.example.project2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Year;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    EditText firstName, aptBuildingNo, streetAddress, cityProvince, pinCode, email, phoneNumber, cardHolderName, cardNumber, cardExpiry, cardCVV, debitCardHolderName, debitCardNumber, debitPostalCode;
    RadioGroup paymentOptions;
    RadioButton creditOption, debitOption;
    LinearLayout creditLayout, debitLayout;
    Button submitOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstName = findViewById(R.id.firstName);
        aptBuildingNo = findViewById(R.id.apt_building_No);
        streetAddress = findViewById(R.id.street_address);
        cityProvince = findViewById(R.id.city);
        pinCode = findViewById(R.id.pin_code);
        email = findViewById(R.id.email1);
        phoneNumber = findViewById(R.id.phone_number);
        cardHolderName = findViewById(R.id.card_holder_name);
        cardNumber = findViewById(R.id.card_number);
        cardExpiry = findViewById(R.id.card_expiry);
        cardCVV = findViewById(R.id.card_cvv);
        debitCardHolderName = findViewById(R.id.debit_card_holder_name);
        debitCardNumber = findViewById(R.id.debit_card_number);
        debitPostalCode = findViewById(R.id.debit_pin_code);
        paymentOptions = findViewById(R.id.payment_options);
        creditOption = findViewById(R.id.credit_option);
        debitOption = findViewById(R.id.debit_option);
        creditLayout = findViewById(R.id.credit_layout);
        debitLayout = findViewById(R.id.debit_layout);
        submitOrder = findViewById(R.id.submit_order);

        paymentOptions.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.credit_option) {
                creditLayout.setVisibility(View.VISIBLE);
            } else {
                creditLayout.setVisibility(View.GONE);
            }
            if (checkedId == R.id.debit_option) {
                debitLayout.setVisibility(View.VISIBLE);
            } else {
                debitLayout.setVisibility(View.GONE);
            }
        });

        submitOrder.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(this, "Order Submitted Successfully", Toast.LENGTH_SHORT).show();
                CartManager cartManager = CartManager.getInstance();
                cartManager.clearCart();
                Intent intent = new Intent(this, ThankyouActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean validateInputs() {
        String FirstName = firstName.getText().toString();
        String AptBuildingNo = aptBuildingNo.getText().toString();
        String StreetAddress = streetAddress.getText().toString();
        String CityProvince = cityProvince.getText().toString();
        String PinCode = pinCode.getText().toString();
        String CardHolderName = cardHolderName.getText().toString();
        String CardExpiry = cardExpiry.getText().toString();
        String DebitCardHolderName = debitCardHolderName.getText().toString();
        String DebitCardNumber = debitCardNumber.getText().toString();
        String DebitPostalCode = debitPostalCode.getText().toString();

        if (TextUtils.isEmpty(firstName.getText())) {
            Toast.makeText(this, "Name is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!FirstName.matches("^[a-zA-Z\\s]+$")) {
            Toast.makeText(this, "Name must be only letters.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(aptBuildingNo.getText())) {
            Toast.makeText(this, "Apt or Building No is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!AptBuildingNo.matches("^[a-zA-Z0-9\\s]+$")) {
            Toast.makeText(this, "Apt or Building No is AlphaNumeric.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(streetAddress.getText())) {
            Toast.makeText(this, "Street Address is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!StreetAddress.matches("^[a-zA-Z0-9\\s]+$")) {
            Toast.makeText(this, "Street Address is AlphaNumeric.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(cityProvince.getText())) {
            Toast.makeText(this, "City and Province is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!CityProvince.matches("^[a-zA-Z\\s]+$")) {
            Toast.makeText(this, "City and Province must have only letters and space.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(pinCode.getText()) || pinCode.getText().length() != 6) {
            Toast.makeText(this, "Postal Code is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!PinCode.matches("^[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]+$")) {
            Toast.makeText(this, "Invalid Postal Code", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(email.getText()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            Toast.makeText(this, "Enter a valid email address.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(phoneNumber.getText()) || phoneNumber.getText().length() != 10) {
            Toast.makeText(this, "Enter a valid 10-digit phone number.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (paymentOptions.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a payment option.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (creditOption.isChecked()) {
            if (TextUtils.isEmpty(cardHolderName.getText())) {
                Toast.makeText(this, "Card Holder Name is required.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!CardHolderName.matches("^[a-zA-Z\\s]+$")) {
                Toast.makeText(this, "Card Holder Name must be only letters.", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(cardNumber.getText()) || cardNumber.getText().length() != 16) {
                Toast.makeText(this, "Enter a valid 16-digit card number.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(cardExpiry.getText())) {
                Toast.makeText(this, "Enter the card expiry date.", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!CardExpiry.matches("^(0[1-9]|1[0-2])\\d{2}$")) {
                Toast.makeText(this, "Enter Valid Date.", Toast.LENGTH_SHORT).show();
                return false;
            }

            String month = CardExpiry.substring(0, 2 );
            String year = "20" + CardExpiry.substring(2);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int currentYear = Year.now().getValue();
                int currentMonth = java.time.LocalDate.now().getMonthValue();
                int expiryYear = Integer.parseInt(year);
                int expiryMonth = Integer.parseInt(month);

                if (expiryYear < currentYear || (expiryYear == currentYear && expiryMonth < currentMonth)) {
                    Toast.makeText(this, "The expiry date must be in the future.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            if (TextUtils.isEmpty(cardCVV.getText()) || cardCVV.getText().length() != 3) {
                Toast.makeText(this, "Enter a valid 3-digit CVV", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (debitOption.isChecked()) {
            if (TextUtils.isEmpty(debitCardHolderName.getText())) {
                Toast.makeText(this, "Card Holder Name is required.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!DebitCardHolderName.matches("^[a-zA-Z\\s]+$")) {
                Toast.makeText(this, "Card Holder Name must be only letters.", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(debitCardNumber.getText()) || debitCardNumber.getText().length() != 16) {
                Toast.makeText(this, "Enter a valid 16-digit card number.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(debitPostalCode.getText()) || debitPostalCode.getText().length() != 6) {
                Toast.makeText(this, "Postal Code is required.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!DebitPostalCode.matches("^[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]+$")) {
                Toast.makeText(this, "Invalid Postal Code", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}