package com.example.ewallet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewallet.model.BankAcount;
import com.example.ewallet.model.FireBaseUserBank;
import com.example.ewallet.model.UserFirebase;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean type = true;
    private Button depositBtn, withdrawBtn, paymentBtn, telecomBtn, cryptoBtn;
    private FireBaseUserBank fireBaseUserBank = new FireBaseUserBank();
    private DatabaseReference mDatabase, m2Database;
    private UserFirebase userFirebase = new UserFirebase();
    private FirebaseUser user;
    private DatabaseReference reference;
    private String id;
    // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    // private String uid= user.getUid();

    public HomeFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        depositBtn = v.findViewById(R.id.withdraw1);
        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type =false;
                setUsetInfo();

            }
        });
        withdrawBtn = v.findViewById(R.id.deposit1);
        withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = true;
                setUsetInfo();
            }
        });
        paymentBtn = v.findViewById(R.id.paymentbtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PaymentActivity.class));
            }
        });
        final TextView txt_balance = v.findViewById(R.id.balance);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        id = user.getUid();
        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long balance = snapshot.child("balance").getValue(Long.class);
                txt_balance.setText(balance.toString() + "d");
            };
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        telecomBtn = v.findViewById(R.id.telecom);
        telecomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TelecomActivity.class));
            }
        });
        cryptoBtn = v.findViewById(R.id.cryptoBtn);
        cryptoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CryptoService.class));
            }
        });
        return v;
    }

    private void setBankUser() {
        // playButton.setText("No bank");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bank").child(userFirebase.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    fireBaseUserBank.setAccount(snapshot.child("account").getValue().toString());
                    fireBaseUserBank.setBank(snapshot.child("bank").getValue().toString());
                    fireBaseUserBank.setId(snapshot.child("id").getValue().toString());
                    fireBaseUserBank.setName(snapshot.child("name").getValue().toString());
                    Long balance = snapshot.child("balance").getValue(Long.class);
                    if(type == true){
                        putActivityDeposit();
                    }
                    else{
                        putActivityWithdraw();;
                    }
                }
                else{
                    if(type == true){
                        putActivityDeposit();
                    }
                    else{
                        putActivityWithdraw();;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(type == true){
                    putActivityDeposit();
                }
                else{
                    putActivityWithdraw();;
                }
            }
        });
    }

    private void setUsetInfo() {
        m2Database = FirebaseDatabase.getInstance().getReference().child("Users").child(userFirebase.getUid());
        m2Database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userFirebase.setBalance(snapshot.child("balance").getValue(Long.class));
                    setBankUser();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }
    private void putActivityWithdraw(){
        Intent intent = new Intent(getActivity(),  Withdraw.class);
        intent.putExtra("keyBank", fireBaseUserBank.getBank());
        intent.putExtra("keyId", fireBaseUserBank.getId());
        intent.putExtra("keyAccount", fireBaseUserBank.getAccount());
        intent.putExtra("keyBalance", userFirebase.getBalance());
        intent.putExtra("keyName", fireBaseUserBank.getName());
        intent.putExtra("success", false);
        startActivity(intent);
    }
    private void putActivityDeposit(){
        Intent intent = new Intent(getActivity(),  Deposit.class);
        intent.putExtra("keyBank", fireBaseUserBank.getBank());
        intent.putExtra("keyId", fireBaseUserBank.getId());
        intent.putExtra("keyAccount", fireBaseUserBank.getAccount());
        intent.putExtra("keyBalance", userFirebase.getBalance());
        intent.putExtra("keyName", fireBaseUserBank.getName());
        intent.putExtra("success", false);
        startActivity(intent);
    }

}
