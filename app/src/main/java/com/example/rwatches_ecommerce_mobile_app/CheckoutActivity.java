package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class CheckoutActivity extends AppCompatActivity {

    ImageView ivBackIconCheckout;
    Button btnPayNow;
    AppDatabase appDatabase;
    int curr_userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        appDatabase = AppDatabase.getInstance(this);

        // Retrieve user_id from the Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user_id")) {
            curr_userID = intent.getIntExtra("user_id", -1);
        }

        ivBackIconCheckout = findViewById(R.id.ivBackIconCheckout);
        btnPayNow = findViewById(R.id.btnPayNow);

        ivBackIconCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validate Input
                validateCheckoutFormInput();

                // Clear cart items
                clearCart(appDatabase, curr_userID);

                // Notify user by email: order placed
                sendEmail(appDatabase, curr_userID); // TODO

                // Thank You page
                Intent intentThankYou = new Intent(getApplicationContext(), ThankYouActivity.class);
                intentThankYou.putExtra("user_id", curr_userID);
                startActivity(intentThankYou);
            }
        });
    }

    // Validate form input fields
    private void validateCheckoutFormInput() {
        // Check if fields are null
    }

    // Clear cart -> update DB
    private void clearCart(AppDatabase appDatabase, int curr_userID) {
        appDatabase.cartDao().clearUserCart(curr_userID);
    }

    public void sendEmail(AppDatabase appDatabase, int curr_userID) {

        String emailSubject;
        StringBuilder emailBody = new StringBuilder();
        UserModel user;

        // Get user details
        user = appDatabase.userDao().getUserById(curr_userID);
        if (user != null) {

            // Recipients
            String[] emailRecipients = { user.getUserEmail() };

            // Construct email body
            emailBody.append("Dear ").append(user.getUserFullName()).append(",\n\n");
            emailBody.append("Thank you for your order!\n\n");
            emailBody.append("Your order has been placed successfully.\n\n");
            emailBody.append("Best regards,\n");
            emailBody.append("R-Watches Team");

            emailSubject = "Order confirmation";

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, emailRecipients);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, (CharSequence) emailBody);

            startActivity(emailIntent);
        }
    }
}
