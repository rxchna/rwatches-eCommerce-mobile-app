package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmailRegisterInputField;
    EditText etFullNameInputField;
    EditText etPasswordRegisterInputField;
    EditText etConfirmPasswordInputField;
    Button btnRegisterNewUser;


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

        btnRegisterNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
            }
        });
    }

}
