package com.example.ewallet;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class BankLink extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_link);
        button = findViewById(R.id.BIDV);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankLink.this, BankInformation.class);
                intent.putExtra("keyBank",button.getText().toString());
                startActivity(intent);

            }
        });

    }
}