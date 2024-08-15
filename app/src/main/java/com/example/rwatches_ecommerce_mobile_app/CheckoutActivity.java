package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class CheckoutActivity extends AppCompatActivity {

    ImageView ivBackIconCheckout;
    Button btnPayNow;
    EditText etFullNameCheckout;
    EditText etPhoneNumberCheckout;
    EditText etMailingAddressCheckout;
    EditText etEmailAddressCheckout;
    EditText etPostalCodeCheckout;
    EditText etCardNumberCheckout;
    EditText etCVVCheckout;
    EditText etExpiryDateCheckout;
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
        etFullNameCheckout = findViewById(R.id.etFullNameCheckout);
        etPhoneNumberCheckout = findViewById(R.id.etPhoneNumberCheckout);
        etEmailAddressCheckout = findViewById(R.id.etEmailAddressCheckout);
        etMailingAddressCheckout = findViewById(R.id.etMailingAddressCheckout);
        etPostalCodeCheckout = findViewById(R.id.etPostalCodeCheckout);
        etCardNumberCheckout = findViewById(R.id.etCardNumberCheckout);
        etCVVCheckout = findViewById(R.id.etCVVCheckout);
        etExpiryDateCheckout = findViewById(R.id.etExpiryDateCheckout);

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
                if(validateCheckoutFormInput(etFullNameCheckout, etPhoneNumberCheckout, etEmailAddressCheckout, etMailingAddressCheckout, etPostalCodeCheckout, etCardNumberCheckout, etCVVCheckout, etExpiryDateCheckout)) {
                    // Clear cart items
                    clearCart(appDatabase, curr_userID);

                    // Notify user by email: order placed
//                    sendEmail(appDatabase, curr_userID);

                    // Thank You page
                    Intent intentThankYou = new Intent(getApplicationContext(), ThankYouActivity.class);
                    intentThankYou.putExtra("user_id", curr_userID);
                    startActivity(intentThankYou);
                }
            }
        });
    }

    // Validate form input fields
    private boolean validateCheckoutFormInput(EditText etFullNameCheckout,
                                           EditText etPhoneNumberCheckout,
                                           EditText etEmailAddressCheckout,
                                           EditText etMailingAddressCheckout,
                                           EditText etPostalCodeCheckout,
                                           EditText etCardNumberCheckout,
                                           EditText etCVVCheckout,
                                           EditText etExpiryDateCheckout) {

        String sFullNameCheckout = etFullNameCheckout.getText().toString().trim();
        String sPhoneNumberCheckout = etPhoneNumberCheckout.getText().toString().trim();
        String sEmailAddressCheckout = etEmailAddressCheckout.getText().toString().trim();
        String sMailingAddressCheckout = etMailingAddressCheckout.getText().toString().trim();
        String sPostalCodeCheckout = etPostalCodeCheckout.getText().toString().trim();
        String sCardNumberCheckout = etCardNumberCheckout.getText().toString().trim();
        String sCVVCheckout = etCVVCheckout.getText().toString().trim();
        String sExpiryDateCheckout = etExpiryDateCheckout.getText().toString().trim();

        // Regular expression for full name
        final String NAME_REGEX = "^[A-Z][a-zA-Z-' ]{1,}[A-Z][a-zA-Z-' ]*$";
        // Regex for email validation
        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        // Regex for Postal Code
        final String POSTAL_CODE_REGEX = "^[A-Z]\\d[A-Z] ?\\d[A-Z]\\d$";
        // Regex for Credit Card
        final String CREDIT_CARD_REGEX = "^(?:\\d{4}[- ]?){3}\\d{4}$"; // pattern for 16-digit cards with optional dashes or spaces
        // Regex for Credit Card
        final String EXPIRY_DATE_REGEX = "^(0[1-9]|1[0-2])/(\\d{2})$"; // MM/YY

        // Check if fields are empty
        if (sFullNameCheckout.isEmpty() ||
                sPhoneNumberCheckout.isEmpty() ||
                sMailingAddressCheckout.isEmpty() ||
                sPostalCodeCheckout.isEmpty() ||
                sCardNumberCheckout.isEmpty() ||
                sEmailAddressCheckout.isEmpty() ||
                sCVVCheckout.isEmpty() ||
                sExpiryDateCheckout.isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Please fill in all details.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Full name validation
        if(!sFullNameCheckout.matches(NAME_REGEX)) {
            Toast.makeText(CheckoutActivity.this, "Please enter a valid name.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Email address validation
        if (!sEmailAddressCheckout.matches(EMAIL_REGEX)) {
            Toast.makeText(CheckoutActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Postal code validation
        if (!sPostalCodeCheckout.matches(POSTAL_CODE_REGEX)) {
            Toast.makeText(CheckoutActivity.this, "Please enter a valid postal code.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Credit Card validation
        if (!sCardNumberCheckout.matches(CREDIT_CARD_REGEX)) {
            Toast.makeText(CheckoutActivity.this, "Please enter a valid credit card number.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Expiry Date validation
        if (!sExpiryDateCheckout.matches(EXPIRY_DATE_REGEX)) {
            Toast.makeText(CheckoutActivity.this, "Please enter a valid card expiry date.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
