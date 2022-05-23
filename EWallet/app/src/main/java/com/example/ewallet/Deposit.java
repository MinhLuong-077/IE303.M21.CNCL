package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Deposit extends AppCompatActivity {
    private Button button,playButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Intent intent = getIntent();
        playButton = findViewById(R.id.bank);
        playButton.setText(intent.getStringExtra("keyBank"));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Deposit");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Deposit.this, HomeFragment.class));
            }
        });
        setSupportActionBar(toolbar);
        button = findViewById(R.id.btnDepositWallet);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Deposit.this, TransactionInvoice.class);
                startActivity(intent);
            }
        });
        button = findViewById(R.id.btnAddBank);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Deposit.this, BankLink.class);
                startActivity(intent);
            }
        });
    }

}