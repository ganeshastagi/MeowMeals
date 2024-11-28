package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText fullName, email, password;
    Button register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        fullName = findViewById(R.id.FullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.register_button);
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user();
            }
        });


    }

    private void user() {
        String Name = fullName.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if (TextUtils.isEmpty(Name)) {
            Toast.makeText(this, "Please enter your Full Name.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Name.matches("^[a-zA-Z\\s]+$")) {
            Toast.makeText(this, "Name must be only letters and spaces.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Please enter the Email.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please enter the Password.",Toast.LENGTH_SHORT).show();
            return;
        }

        if (Password.length() < 6) {
            Toast.makeText(this, "Please enter the password of at least 5 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Password.matches(".*[0-9].*")) {
            Toast.makeText(this, "Password must contain at least a number.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Password.matches(".*[!@#$%^&*()-+=].*")) {
            Toast.makeText(this, "Password must contain at least a symbol.", Toast.LENGTH_SHORT).show();
        }
        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Successfully Registered!!!", Toast.LENGTH_SHORT).show();
                    fullName.setText("");
                    email.setText("");
                    password.setText("");
                }
                else {
                    String errorMessage;
                    Exception exception = task.getException();
                    if (exception instanceof com.google.firebase.auth.FirebaseAuthUserCollisionException) {
                        errorMessage = "This email is already registered. Please use another email.";
                    } else if (exception instanceof com.google.firebase.auth.FirebaseAuthInvalidCredentialsException) {
                        errorMessage = "Please enter a valid email address.";
                    } else if (exception instanceof com.google.firebase.auth.FirebaseAuthWeakPasswordException) {
                        errorMessage = "Password is too weak. Use a strong password.";
                    } else {
                        errorMessage = "Registration failed. Please try again.";
                    }
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}