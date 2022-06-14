package com.example.ewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class PaymentActivity extends AppCompatActivity {
        Button elecBtn, waterBtn;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_payment);
            elecBtn = findViewById(R.id.elecbtn);
            waterBtn = findViewById(R.id.waterbtn);
            elecBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(com.example.ewallet.PaymentActivity.this, ElectricityActivity.class));
                }
            });
            waterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(com.example.ewallet.PaymentActivity.this, WaterActivity.class));
                }
            });
        }
}