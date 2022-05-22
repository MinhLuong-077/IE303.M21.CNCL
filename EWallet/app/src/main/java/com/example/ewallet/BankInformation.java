package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewallet.api.ApiBank;
import com.example.ewallet.model.BankAcount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BankInformation extends AppCompatActivity {
    private Button button;
    private TextInputLayout account;
    private TextInputLayout name;
    private TextInputLayout keyId;
    private String valueKeyBank;
    private  String txt_account;
    private  String txt_name;
    private String txt_keyId;

    private int txt_otp;
    private DatabaseReference mDatabase;
    private boolean success = false;
    public static final int getStatusCode = 200;
    public static final float minimumMoney = 50000;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid= user.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_information);
        Intent intent = getIntent();
        valueKeyBank = intent.getStringExtra("keyBank");
        TextView mTextView = (TextView) findViewById(R.id.mytextview6);
        mTextView.setText(mTextView.getText().toString() + "\nBANK " + valueKeyBank);
        checkBankUser();
        button = findViewById(R.id.btnContinue1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = findViewById(R.id.account);
                name = findViewById(R.id.name);
                keyId = findViewById(R.id.keyId);
                txt_account = account.getEditText().getText().toString();
                txt_name = name.getEditText().getText().toString();
                txt_keyId = keyId.getEditText().getText().toString();
                 if(checkInputInformation()){
                     checkBankExist();
                     clickCallApiBank();
                 }
                 else
                 {
                     Toast.makeText(BankInformation.this, "Please enter full information", Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }

    private void checkBankUser() {
//        uid = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bank").child(uid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    button.setEnabled(false);
                    button.setText("CANCEL");
                }
            else {
                    button.setEnabled(true);             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                button.setEnabled(false);
                button.setText("CANCEL");
            }
        });
    }

    private void clickCallApiBank() {
        ApiBank.apibank.callApiBank(txt_account,valueKeyBank).enqueue(new Callback<BankAcount>() {
            @Override
            public void onResponse(Call<BankAcount> call, Response<BankAcount> response) {
                if(response.code()==getStatusCode){
                    BankAcount bankAcount = response.body();
                    String txt_accountBank = bankAcount.getAccount();
                    String txt_nameBank = bankAcount.getName();
                    String txt_IdBank = bankAcount.getID();

                    if(checkBankInformationUser(txt_accountBank,txt_nameBank,txt_IdBank)){
                       if(bankAcount.getMoney()>=minimumMoney){
                           if(!success){
                               Intent intent = new Intent(BankInformation.this, OTPBank.class);
                               intent.putExtra("keyUid",uid);
                               intent.putExtra("keyAccountBank",txt_accountBank);
                               intent.putExtra("keyNameBank",txt_nameBank);
                               intent.putExtra("keyIdBank",txt_IdBank );
                               intent.putExtra("keyBank",bankAcount.getBank());
                               intent.putExtra("keyTelephone",bankAcount.getTelephone());
//                               intent.putExtra("keyMoney",bankAcount.getMoney());
                               startActivity(intent);
                           }
                           else {
                               Toast.makeText(BankInformation.this,"Account already linked", Toast.LENGTH_SHORT).show();

                           }
                       }
                       else {
                           Toast.makeText(BankInformation.this,"Accounts not enough condition", Toast.LENGTH_SHORT).show();

                       }

                    }
                    else{
                        Toast.makeText(BankInformation.this,"Account does not exist", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(BankInformation.this,"Account does not exist", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<BankAcount> call, Throwable t) {
                Toast.makeText(BankInformation.this,"System error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean checkBankInformationUser(String txt_account, String txt_name, String txt_id) {
        String txt_accountInput = account.getEditText().getText().toString();
        String txt_nameInput  = name.getEditText().getText().toString();
        String txt_keyIdInput  = keyId.getEditText().getText().toString();
        if(txt_account.equalsIgnoreCase(txt_accountInput) && txt_name.equalsIgnoreCase(txt_nameInput ) && txt_keyIdInput.equalsIgnoreCase(txt_id))
        {
            return true;
        }
            return false;
    }


    private void checkBankExist() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BankLink").child(valueKeyBank);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    if(txt_account.equalsIgnoreCase(dataSnapshot.getValue().toString())){
                        success = true;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BankInformation.this,"System error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkInputInformation() {

        if(TextUtils.isEmpty(txt_account) || TextUtils.isEmpty(txt_name)|| TextUtils.isEmpty(txt_keyId)){
            return false;
        }
        return true;
    }







}
