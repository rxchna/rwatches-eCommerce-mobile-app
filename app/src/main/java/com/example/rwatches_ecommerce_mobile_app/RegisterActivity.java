package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmailRegisterInputField;
    EditText etFullNameInputField;
    EditText etPasswordRegisterInputField;
    EditText etConfirmPasswordInputField;
    Button btnRegisterNewUser;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etEmailRegisterInputField = findViewById(R.id.emailRegisterInputField);
        etFullNameInputField = findViewById(R.id.fullNameInputField);
        etPasswordRegisterInputField = findViewById(R.id.passwordRegisterInputField);
        etConfirmPasswordInputField = findViewById(R.id.confirmPasswordInputField);
        btnRegisterNewUser = findViewById(R.id.btnRegisterNewUser);

        appDatabase = AppDatabase.getInstance(this);

        btnRegisterNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regular expression for email validation
                final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

                String emailInput = etEmailRegisterInputField.getText().toString().trim();
                String passwordInput = etPasswordRegisterInputField.getText().toString().trim();
                String confirmPasswordInput = etConfirmPasswordInputField.getText().toString().trim();
                String fullNameInput = etFullNameInputField.getText().toString().trim();

                // Input validation
                if (emailInput.isEmpty() || passwordInput.isEmpty() || confirmPasswordInput.isEmpty() || fullNameInput.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Email validation
                if (!emailInput.matches(EMAIL_REGEX)) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if password matches
                if (!passwordInput.equals(confirmPasswordInput)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if user already exists
                UserModel existingUser = appDatabase.userDao().getUser(emailInput);
                if (existingUser != null) {
                    Toast.makeText(RegisterActivity.this, "User email already exists.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create new user model
                UserModel newUser = new UserModel(emailInput, passwordInput, fullNameInput);
                appDatabase.userDao().insertUser(newUser);

                // Notify user and navigate to login
                Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });
    }

}
