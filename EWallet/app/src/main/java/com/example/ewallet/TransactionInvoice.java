package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewallet.api.ApiBank;
import com.example.ewallet.model.BankAcount;
import com.example.ewallet.model.PutData;
import com.example.ewallet.model.TransactionBank;
import com.example.ewallet.model.UserFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionInvoice extends AppCompatActivity {
    private Button button;
    private String valueBank,valueID,valueAccount;
    private Long valueAmount;
    private UserFirebase userFirebase = new UserFirebase();
    private int valueType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_invoice);
        Intent intent = getIntent();
        valueBank = intent.getStringExtra("keyBankTwo");
        valueAmount = intent.getLongExtra("keyAmount",0);
        valueID = intent.getStringExtra("keyID");
        valueType = intent.getIntExtra("keyAdd",0);
        valueAccount = intent.getStringExtra("keyAccount");
        userFirebase.setBalance(intent.getLongExtra("keyBalance",0));
        TextView mTextView = (TextView) findViewById(R.id.txtBank);
        mTextView.setText(valueBank + "\n\n" + valueAmount);
        button = findViewById(R.id.btnContinueDepositConfirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTransaction();
            }
        });
    }

    private void createTransaction() {
        if(transactionBank()){
            transactionFirebaseUser();
            startActivity(new Intent(TransactionInvoice.this, MainActivity.class));
        }
    }
    private void transactionFirebaseUser() {
       if(valueType==1 | valueType ==2){
           Long sumBalance ;
           sumBalance = 0L;
           TransactionBank transactionBank = new TransactionBank();
           if(valueType==1){
               sumBalance = userFirebase.getBalance() + valueAmount;
               transactionBank.setType("Deposit");
           }
           if(valueType==2){
               sumBalance = userFirebase.getBalance() - valueAmount;
               transactionBank.setType("Withdraw");
           }
           userFirebase.setBalance(sumBalance) ;
           DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
           mDatabase.child("Users").child(userFirebase.getUid()).child("balance").setValue(userFirebase.getBalance());
           transactionBank.setBank(valueBank);
           transactionBank.setAccount(valueAccount);
           transactionBank.setMoney(valueAmount);
           UUID uuid = UUID.randomUUID();
           transactionBank.setId(uuid.toString());
           Date currentTime = Calendar.getInstance().getTime();
           transactionBank.setTime(getDateStringOne(currentTime));
           DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("BankingTransaction").child(getDateString(currentTime)).child(userFirebase.getUid()).child(uuid.toString());
           mDatabase1.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   transactionBank.setStatus("Done");
                   mDatabase1.setValue(transactionBank);
               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   transactionBank.setStatus("Error");
                   mDatabase1.setValue(transactionBank);
               }
           });
       }
    }


    private boolean transactionBank() {
        PutData updateResponse = new PutData();
        String strI = String.valueOf(valueType);
        updateResponse.setStatus(strI);
        updateResponse.setMoney(valueAmount);
        Call<PutData> call = ApiBank.apibank.updateUser(valueAccount,valueBank,valueID, updateResponse);
        call.enqueue(new Callback<PutData>() {
            @Override
            public void onResponse(Call<PutData> call, Response<PutData> response) {

            }

            @Override
            public void onFailure(Call<PutData> call, Throwable t) {

            }
        });
//        ApiBank.apibank.updateUser(valueAccount,valueBank,valueID, updateResponse);
//        ApiBank.apibank.updateUser(valueAccount,valueBank,valueID,updateResponse).enqueue(new Callback<BankAcount>() {
//            @Override
//            public void onResponse(Call<BankAcount> call, Response<BankAcount> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<BankAcount> call, Throwable t) {
//
//            }
//        });
        return true;
    }
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    public static String getDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);}

    private static final String DATE_FORMATONE = "yyyy-MM-dd HH:mm:ss";
    public static String getDateStringOne(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMATONE);
        return format.format(date);
    }
}