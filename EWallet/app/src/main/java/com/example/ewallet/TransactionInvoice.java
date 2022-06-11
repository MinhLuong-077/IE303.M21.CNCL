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

import com.example.ewallet.api.ApiBank;
import com.example.ewallet.model.PutData;
import com.example.ewallet.model.TransactionBankingAPI;
import com.example.ewallet.model.TransactionFirebaseUser;
import com.example.ewallet.model.UserFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionInvoice extends AppCompatActivity {
    private Button button;
    private String valueBank, valueID, valueAccount, valueName;
    private Boolean success = false;
    private Long valueAmount;
    private UserFirebase userFirebase = new UserFirebase();
    private int valueType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_invoice);
        Intent intent = getIntent();
        valueBank = intent.getStringExtra("keyBankTwo");
        valueName = intent.getStringExtra("keyName");
        valueAmount = intent.getLongExtra("keyAmount", 0);
        valueID = intent.getStringExtra("keyID");
        valueType = intent.getIntExtra("keyAdd", 0);
        valueAccount = intent.getStringExtra("keyAccount");
        userFirebase.setBalance(intent.getLongExtra("keyBalance", 0));
        TextView mTextView = (TextView) findViewById(R.id.bank);
        mTextView.setText(valueBank + "\n\n" + valueAmount);
        TextView mTextView1 = (TextView) findViewById(R.id.amount);
        mTextView1.setText(Long.toString(valueAmount));

        button = findViewById(R.id.btnContinueDepositConfirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBankMoney();
                // createTransaction();
            }
        });
    }
    private UUID uuid = UUID.randomUUID();
    private void transactionFirebaseUser() {
        if (valueType == 1 | valueType == 2) {
            Long sumBalance;
            sumBalance = 0L;
            TransactionFirebaseUser transactionFirebaseUser = new TransactionFirebaseUser();
            if (valueType == 1) {
                sumBalance = userFirebase.getBalance() + valueAmount;
                transactionFirebaseUser.setType("Deposit");
            }
            if (valueType == 2) {
                sumBalance = userFirebase.getBalance() - valueAmount;
                transactionFirebaseUser.setType("Withdraw");
            }
            userFirebase.setBalance(sumBalance);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").child(userFirebase.getUid()).child("balance").setValue(userFirebase.getBalance());
            transactionFirebaseUser.setBank(valueBank);
            transactionFirebaseUser.setAccount(valueAccount);
            transactionFirebaseUser.setMoney(valueAmount);
            transactionFirebaseUser.setId(uuid.toString());
            Date currentTime = Calendar.getInstance().getTime();
            transactionFirebaseUser.setTime(getDateStringOne(currentTime));
            DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("BankingTransaction")
                    .child(userFirebase.getUid()).child(getDateString(currentTime)).child(uuid.toString());
            mDatabase1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    transactionFirebaseUser.setStatus("Done");
                    mDatabase1.setValue(transactionFirebaseUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    transactionFirebaseUser.setStatus("Error");
                    mDatabase1.setValue(transactionFirebaseUser);
                }
            });
        }
    }

    private void handleBankMoney() {
        PutData updateResponse = new PutData();
        String strI = String.valueOf(valueType);
        updateResponse.setStatus(strI);
        updateResponse.setMoney(valueAmount);
        Toast.makeText( TransactionInvoice.this, "Maximum " +valueAccount + valueBank + valueID,
                Toast.LENGTH_SHORT).show();
        Call<PutData> call = ApiBank.apibank.updateUser(valueAccount, valueBank, valueID, updateResponse);
        call.enqueue(new Callback<PutData>() {
            @Override
            public void onResponse(Call<PutData> call, Response<PutData> response) {
                transactionBank();
                transactionFirebaseUser();
                startActivity(new Intent(TransactionInvoice.this, MainActivity.class));

            }

            @Override
            public void onFailure(Call<PutData> call, Throwable t) {
                success = false;
            }
        });

    }

    private void transactionBank() {
        TransactionBankingAPI transactionBankingAPI = new TransactionBankingAPI();
        if (valueType == 1) {
            transactionBankingAPI.setStatus("Deposit");
            transactionBankingAPI.setMessage("Deposit successful????");
        }
        if (valueType == 2) {
            transactionBankingAPI.setStatus("Withdraw");
            transactionBankingAPI.setMessage("Deposit successful????");

        }
        transactionBankingAPI.setUserId(valueID);
        transactionBankingAPI.setID(uuid.toString());
        transactionBankingAPI.setDate(Calendar.getInstance().getTime());
        transactionBankingAPI.setName(valueName);
        transactionBankingAPI.setAccount(valueAccount);
        transactionBankingAPI.setBank(valueBank);
        transactionBankingAPI.setMoney(valueAmount);

        Call<TransactionBankingAPI> call = ApiBank.apibank.createTransaction(transactionBankingAPI);
        call.enqueue(new Callback<TransactionBankingAPI>() {
            @Override
            public void onResponse(Call<TransactionBankingAPI> call, Response<TransactionBankingAPI> response) {

            }

            @Override
            public void onFailure(Call<TransactionBankingAPI> call, Throwable t) {
            }
        });
    }

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public static String getDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    private static final String DATE_FORMATONE = "yyyy-MM-dd HH:mm:ss";

    public static String getDateStringOne(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMATONE);
        return format.format(date);
    }

}