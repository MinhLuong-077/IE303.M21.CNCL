package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewallet.model.BankAcount;
import com.example.ewallet.utils.DAOUsers;
import com.example.ewallet.utils.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OTPBank extends AppCompatActivity {
    private Button button;
    private String valueUid;
    private String valueAccount;
    private String valueName;
    private String valueIdBank;
    private String valueBank;
    private String valueTelephone;
    private TextInputLayout txtVerOTP;
    private String verificationId;
//    private float valueMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpbank);
        button = findViewById(R.id.btnContinueVerOTP);
        txtVerOTP = findViewById(R.id.verOTP);
        otpBank();
        Intent intent = getIntent();
        valueUid = intent.getStringExtra("keyUid");
        valueAccount = intent.getStringExtra("keyAccountBank");
        valueName = intent.getStringExtra("keyNameBank");
        valueIdBank = intent.getStringExtra("keyIdBank");
        valueBank = intent.getStringExtra("keyBank");
        valueTelephone= intent.getStringExtra("keyTelephone");
//        valueMoney = intent.getFloatExtra("keyMoney",0);
        TextView mTextView = (TextView) findViewById(R.id.mytextview22122);
        mTextView.setText(valueAccount + "\n\n" + valueName+ "\n\n"+valueIdBank);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_VerOT = txtVerOTP.getEditText().getText().toString();
                if(TextUtils.isEmpty(txt_VerOT)){
                    Toast.makeText(OTPBank.this, "please enter the OTP code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    verifyCode(txt_VerOT);
                }

            }
        });
    }

    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
    private void addDataBankFirebase() {
            createBankLinkUserFireBase();
            addDatatoFirebase();
    }

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceName;
    private void createBankLinkUserFireBase() {
        databaseReferenceName = FirebaseDatabase.getInstance().getReference().child("BankLink").child(valueBank).child(valueUid);
        databaseReferenceName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReferenceName.setValue(valueAccount);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OTPBank.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void addDatatoFirebase() {
        BankAcount bankAcount = new BankAcount();
        bankAcount.setAccount(valueAccount);
        bankAcount.setBank(valueBank);
        bankAcount.setID(valueIdBank);
        bankAcount.setName(valueName);
        bankAcount.setTelephone(valueTelephone);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bank").child(valueUid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(bankAcount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OTPBank.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();

            }
        });

    }
    private FirebaseAuth mAuth;
    private void otpBank(){
        mAuth = FirebaseAuth.getInstance();
        String phone = "+84" + "0968569549";
        sendVerificationCode(phone);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addDataBankFirebase();
                            startActivity(new Intent(OTPBank.this, Deposit.class));
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(OTPBank.this, "OTP code is incorrect", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPBank.this, "OTP code is incorrect", Toast.LENGTH_LONG).show();
        }
    };

}