package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

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
                Intent intentThankYou = new Intent(getApplicationContext(), ThankYouActivity.class);
                startActivity(intentThankYou);
            }
        });
    }
}
