package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ElectricityActivity extends AppCompatActivity {
    TextInputLayout cusId;
    Button checkBtn;
    private DatabaseReference billReference, userReference;
    private FirebaseUser user;
    private String userId;
    private String balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        cusId = findViewById(R.id.customer_id);
        checkBtn = findViewById(R.id.checkBill);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        userReference = FirebaseDatabase.getInstance().getReference("Users");
        billReference = FirebaseDatabase.getInstance().getReference("Bills/Electricity");
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = cusId.getEditText().getText().toString();
                billReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long cost = snapshot.child("Cost").getValue(Long.class);
                        userReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Long balance = snapshot.child("balance").getValue(Long.class);
                                if (balance < cost){
                                    Toast.makeText(ElectricityActivity.this, "Not enough money", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Long amount = balance - cost;
                                    userReference.child(userId).child("balance").setValue(amount);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    };
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        startActivity(new Intent(ElectricityActivity.this, MainActivity.class));
            }
        });
    }
}