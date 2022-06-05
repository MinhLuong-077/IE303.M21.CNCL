package com.example.ewallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ewallet.model.TransactionGroupItem;
import com.example.ewallet.model.TransactionItem;

import java.util.ArrayList;
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

        RecyclerView GroupRecyclerViewItem
                = (RecyclerView) v.findViewById(R.id.transaction_group_recyclerview);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity());

        TransactionGroupAdapter
                transactionGroupAdapter
                = new TransactionGroupAdapter(
                        TransactionGroupListItem());

        GroupRecyclerViewItem
                .setAdapter(transactionGroupAdapter);
        GroupRecyclerViewItem
                .setLayoutManager(layoutManager);

        return v;
    }

    private List<TransactionGroupItem> TransactionGroupListItem()
    {
        List<TransactionGroupItem> itemList
                = new ArrayList<>();
        // Ae get data tu db xong gan vao trong item duoi day nha

        TransactionGroupItem item
                = new TransactionGroupItem(
                "June 2022",
                      TransactionListItem());
        itemList.add(item);

        TransactionGroupItem item1
                = new TransactionGroupItem(
                "July 2022",
                TransactionListItem());
        itemList.add(item1);

        TransactionGroupItem item2
                = new TransactionGroupItem(
                "August 2022",
                TransactionListItem());
        itemList.add(item2);

        TransactionGroupItem item3
                = new TransactionGroupItem(
                "September 2022",
                TransactionListItem());
        itemList.add(item3);

        return itemList;
    }

    private List<TransactionItem> TransactionListItem()
    {
        List<TransactionItem> TransactionListItem
                = new ArrayList<>();

        TransactionListItem.add(new TransactionItem("Chuyen tien cho Son", "8:00 - 05/06/2022", "-150.000"));
        TransactionListItem.add(new TransactionItem("Chuyen tien cho Luong", "9:00 - 05/06/2022", "-200.000"));
        TransactionListItem.add(new TransactionItem("Chuyen tien cho Tu", "10:00 - 05/06/2022", "-250.000"));
        TransactionListItem.add(new TransactionItem("Quan tra no", "11:00 - 05/06/2022", "+1.000.000"));

        return TransactionListItem;
    }
}
