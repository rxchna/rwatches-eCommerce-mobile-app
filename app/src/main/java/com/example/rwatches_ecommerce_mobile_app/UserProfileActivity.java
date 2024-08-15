package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class UserProfileActivity extends AppCompatActivity {

    private EditText userEmailDetail;
    private EditText userFullNameDetail;

    private Button btnLogout;

    private

    int curr_userID;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        userEmailDetail = findViewById(R.id.userEmailDetailField);
        userFullNameDetail = findViewById(R.id.userFullNameDetailField);
        btnLogout = findViewById(R.id.btnLogout);

        appDatabase = AppDatabase.getInstance(this);

        // Retrieve user_id from the Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user_id")) {
            curr_userID = intent.getIntExtra("user_id", -1);
        }

        // Get user data
        UserModel user = appDatabase.userDao().getUserById(curr_userID);

        String userEmail = user.getUserEmail();
        String userFullName = user.getUserFullName();

        // Set data to UI components
        if (userEmail != null) {
            userEmailDetail.setText(userEmail);
        }
        if (userFullName != null) {
            userFullNameDetail.setText(userFullName);
        }

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMain);

                // Show Logout Toast
                Toast.makeText(UserProfileActivity.this, "You have been logged out successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
