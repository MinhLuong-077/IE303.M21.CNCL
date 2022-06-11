package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewallet.api.ApiBank;
import com.example.ewallet.model.BankAcount;
import com.example.ewallet.model.FireBaseUserBank;
import com.example.ewallet.model.UserFirebase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Deposit extends AppCompatActivity {
    private Button button, playButton, buttonTrue, buttonDeposit;
    private Long money;
    private Long txt_amount;
    private TextInputEditText amount1;
    private String valueKeyBank;
    private String txt_account, txt_name;
    private String txt_id;
    private String txt_bank;
    private float txt_money;
    public static final int getStatusCode = 200;
    public static final float minimumMoney = 50000;
    public static final float minimumDepositMoney = 10000;
    public static final float maxDepositMoney = 10000000;
    private UserFirebase userFirebase = new UserFirebase();
    private static final int typeDeposit = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        setUsetInfo();
        setData();
        // txt_amount = 100000;
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
        buttonDeposit = findViewById(R.id.btnDepositWallet);
        buttonDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUsetInfo();
                setData();
                clickCallApiBank();
            }
        });

        buttonTrue = findViewById(R.id.btnAddBank);
        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Deposit.this, BankLink.class);
                startActivity(intent);
                finish();
            }
        });

        button = findViewById(R.id.btn50000Thousand);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mTextView = (EditText) findViewById(R.id.txtAmount);
                mTextView.setText("500000");
            }
        });
        button = findViewById(R.id.btn20000Thousand);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mTextView = (EditText) findViewById(R.id.txtAmount);
                mTextView.setText("200000");
            }
        });
        button = findViewById(R.id.btn10000Thousand);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mTextView = (EditText) findViewById(R.id.txtAmount);
                mTextView.setText("1000000");
            }
        });
    }

    private boolean checkMoney() {
        if ((txt_money - minimumMoney) >= txt_amount) {
            return true;
        }
        return false;
    }

    private void setUsetInfo() {
        DatabaseReference m2Database;
        m2Database = FirebaseDatabase.getInstance().getReference().child("Users").child(userFirebase.getUid());
        m2Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                money = snapshot.child("balance").getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // private DatabaseReference mDatabase;
    // private FireBaseUserBank fireBaseUserBank = new FireBaseUserBank ();
    // private void setBankUser() {
    // mDatabase =
    // FirebaseDatabase.getInstance().getReference().child("Bank").child(userFirebase.getUid());
    // mDatabase.addValueEventListener(new ValueEventListener() {
    // @Override
    // public void onDataChange(@NonNull DataSnapshot snapshot) {
    // if (snapshot.exists()) {
    // fireBaseUserBank.setAccount(snapshot.child("account").getValue().toString());
    // fireBaseUserBank.setBank(snapshot.child("bank").getValue().toString());
    // fireBaseUserBank.setId(snapshot.child("id").getValue().toString());
    // }
    // }
    // @Override
    // public void onCancelled(@NonNull DatabaseError error) {
    //
    // }
    // });
    // }
    private void setData() {
        Intent intent = getIntent();
        playButton = findViewById(R.id.bank);
        txt_bank = intent.getStringExtra("keyBank");
        txt_name = intent.getStringExtra("keyName");
        playButton.setText(txt_bank);
        TextView mTextView = (TextView) findViewById(R.id.balance);
        money = intent.getLongExtra("keyBalance", 0);
        mTextView.setText(money.toString());

    }

    private void clickCallApiBank() {
        Intent intent = getIntent();
        valueKeyBank = intent.getStringExtra("keyBank");
        txt_account = intent.getStringExtra("keyAccount");
        txt_id = intent.getStringExtra("keyId");
        ApiBank.apibank.callApiBank(txt_account, valueKeyBank).enqueue(new Callback<BankAcount>() {
            @Override
            public void onResponse(Call<BankAcount> call, Response<BankAcount> response) {
                if (response.code() == getStatusCode) {
                    BankAcount bankAcount = response.body();
                    txt_money = bankAcount.getMoney();
                    if(valueKeyBank != null){
                        checkBankUser();
                    }
                    else
                    {
                        Toast.makeText(Deposit.this, "Please link your bank" +valueKeyBank,
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Deposit.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BankAcount> call, Throwable t) {
                Toast.makeText(Deposit.this, "Please link your bank +332323232", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkBankUser() {

//        Intent intent = getIntent();
//        txt_account = intent.getStringExtra("keyAccount");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("BankLink").child(valueKeyBank);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    if(txt_account.equalsIgnoreCase(dataSnapshot.getValue().toString())){
                        bankTransferActivity();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void bankTransferActivity() {
        amount1 = findViewById(R.id.txtAmount);
        txt_amount = Long.parseLong(amount1.getText().toString());
        if ((txt_amount >= minimumDepositMoney) && (txt_amount <= maxDepositMoney)) {
            if (checkMoney()) {
                Intent intent = new Intent(Deposit.this, TransactionInvoice.class);
                intent.putExtra("keyBankTwo", txt_bank);
                intent.putExtra("keyAccount", txt_account);
                intent.putExtra("keyID", txt_id);
                intent.putExtra("keyAmount", txt_amount);
                intent.putExtra("keyBalance", money);
                intent.putExtra("keyName", txt_name);
                intent.putExtra("keyAdd", typeDeposit);
                startActivity(intent);
            } else {
                Toast.makeText(Deposit.this, "The balance in the bank is not enough", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(Deposit.this, "Maximum deposit is 10.000.000 and minimum is 10.000",
                    Toast.LENGTH_SHORT).show();
        }
    }


}

