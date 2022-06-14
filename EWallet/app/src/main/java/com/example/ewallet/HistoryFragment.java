package com.example.ewallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ewallet.model.TransactionGroupItem;
import com.example.ewallet.model.TransactionItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String id;
    private  List<TransactionGroupItem> itemList
            = new ArrayList<>();
    List<TransactionItem> TransactionListItem
            = new ArrayList<>();
    public HistoryFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("BankingTransaction");
        id = user.getUid();
        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String date = snapshot.getKey().toString();
                    for (DataSnapshot snapshot1 : dataSnapshot.child(date).getChildren()){
                        String message = snapshot1.child("type").getValue().toString();
                        String time = snapshot1.child("time").getValue().toString();
                        String money = snapshot1.child("money").getValue().toString();
                        TransactionListItem.add(new TransactionItem(message , time, money));
                    }
                    TransactionGroupItem item
                            = new TransactionGroupItem(
                            date,
                            TransactionListItem);
                    itemList.add(item);
                    Collections.reverse(itemList);
                }
                RecyclerView GroupRecyclerViewItem
                        = (RecyclerView) v.findViewById(R.id.transaction_group_recyclerview);

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getActivity());

                TransactionGroupAdapter
                        transactionGroupAdapter
                        = new TransactionGroupAdapter(
                        TransactionGroupListItem(itemList));

                GroupRecyclerViewItem
                        .setAdapter(transactionGroupAdapter);
                GroupRecyclerViewItem
                        .setLayoutManager(layoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return v;
    }


    private List<TransactionGroupItem> TransactionGroupListItem(List itemList)
    {
        return itemList;
    }
//
//    private List<TransactionItem> TransactionListItem()
//    {
//        List<TransactionItem> TransactionListItem
//                = new ArrayList<>();
//
//        TransactionListItem.add(new TransactionItem("Chuyen tien cho Son", "8:00 - 05/06/2022", "-150.000"));
//        TransactionListItem.add(new TransactionItem("Chuyen tien cho Luong", "9:00 - 05/06/2022", "-200.000"));
//        TransactionListItem.add(new TransactionItem("Chuyen tien cho Tu", "10:00 - 05/06/2022", "-250.000"));
//        TransactionListItem.add(new TransactionItem("Quan tra no", "11:00 - 05/06/2022", "+1.000.000"));
//
//        return TransactionListItem;
//    }
}
