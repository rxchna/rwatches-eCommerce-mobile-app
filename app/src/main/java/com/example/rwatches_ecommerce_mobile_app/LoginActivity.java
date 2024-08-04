package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText etLoginEmail;
    EditText etLoginPassword;
    Button btnLogin;
    Button btnRegister;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.emailInputField);
        etLoginPassword = findViewById(R.id.passwordInputField);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        appDatabase = AppDatabase.getInstance(this);

        // Add event handlers to buttons
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = etLoginEmail.getText().toString().trim();
                String passwordInput = etLoginPassword.getText().toString().trim();

                // Regex for email inout validation
                final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

                // Input validation
                if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Email validation
                if (!emailInput.matches(EMAIL_REGEX)) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if user exists
                UserModel user = appDatabase.userDao().getUserByEmailAndPassword(emailInput, passwordInput);
                if (user == null) {
                    // Failed login
                    Toast.makeText(LoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Successful login
                Intent intentProducts = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intentProducts);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }
}
