package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ewallet.model.TransactionGroupItem;

import java.util.List;

public class TransactionGroupAdapter
        extends RecyclerView.Adapter<TransactionGroupAdapter.TransactionGroupViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<TransactionGroupItem> itemList;

    TransactionGroupAdapter(List<TransactionGroupItem> itemList)
    {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public TransactionGroupViewHolder onCreateViewHolder(
        @NonNull ViewGroup viewGroup,
        int i)
    {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.history_transaction_group_item,
                        viewGroup, false);
        return new TransactionGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
        @NonNull TransactionGroupViewHolder transactionGroupViewHolder,
        int position)
    {
        TransactionGroupItem transactionGroupItem = itemList.get(position);
        transactionGroupViewHolder.MonthYear.setText(transactionGroupItem.getmYear());

        // assign layout to recycler view
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                        transactionGroupViewHolder
                                .ItemRecyclerView
                                .getContext(),
                        LinearLayoutManager.VERTICAL,
                        false);

        layoutManager.setInitialPrefetchItemCount(
                transactionGroupItem
                        .getTransactionListItem()
                        .size());

        TransactionItemAdapter transactionItemAdapter
                = new TransactionItemAdapter(
                        transactionGroupItem
                                .getTransactionListItem());
        transactionGroupViewHolder
                .ItemRecyclerView
                .setLayoutManager(layoutManager);
        transactionGroupViewHolder
                .ItemRecyclerView
                .setAdapter(transactionItemAdapter);
        transactionGroupViewHolder
                .ItemRecyclerView
                .setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // class extend from viewholder
    class TransactionGroupViewHolder
        extends RecyclerView.ViewHolder {

        private TextView MonthYear;
        private RecyclerView ItemRecyclerView;

        TransactionGroupViewHolder(final View itemView)
        {
            super(itemView);

            MonthYear = itemView.findViewById(R.id.month_year_group);
            ItemRecyclerView = itemView.findViewById(R.id.transaction_item_recyclerview);
        }
    }
}
