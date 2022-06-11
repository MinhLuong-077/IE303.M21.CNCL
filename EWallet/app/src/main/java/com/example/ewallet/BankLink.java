package com.example.ewallet;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class BankLink extends AppCompatActivity implements View.OnClickListener {
    private Button btnBIDV,btnAgribank,btnVietinBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_link);

//        button = findViewById(R.id.Vietcombank);
        btnBIDV = findViewById(R.id.BIDV);
        btnAgribank = findViewById(R.id.Agribank);
        btnVietinBank = findViewById(R.id.VietinBank);

        // apply setOnClickListener over buttons
        btnBIDV.setOnClickListener(this);
        btnAgribank.setOnClickListener(this);
        btnVietinBank.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BIDV:
                createData(btnBIDV);
                break;
            case R.id.Agribank:
                createData(btnAgribank);
                break;
            // even more buttons here
            case R.id.VietinBank:
                createData(btnVietinBank);
                break;
            // even more buttons here
        }



    }
    private void createData(Button button) {
        Intent intent = new Intent(BankLink.this, BankInformation.class);
        intent.putExtra("keyBank",button.getText().toString());
        startActivity(intent);
    }

}