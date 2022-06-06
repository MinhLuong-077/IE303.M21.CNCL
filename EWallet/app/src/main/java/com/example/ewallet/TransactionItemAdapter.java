package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ewallet.R;
import com.example.ewallet.model.TransactionItem;

import java.util.List;

public class TransactionItemAdapter extends RecyclerView.Adapter<TransactionItemAdapter.TransactionViewHolder> {
    private List<TransactionItem> TransactionListItem;

    // constructor
    TransactionItemAdapter(List<TransactionItem> transactionListItem)
    {
        this.TransactionListItem = transactionListItem;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(
        @NonNull ViewGroup viewGroup,
        int i)
    {
        View view = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(
                                R.layout.history_transaction_item,
                                viewGroup, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
        @NonNull TransactionViewHolder transactionViewHolder,
        int position)
    {
        TransactionItem transactionItem = TransactionListItem.get(position);
        transactionViewHolder.TransactionMessage.setText(transactionItem.getMessage());
        transactionViewHolder.Datetime.setText(transactionItem.getDatetime());
        transactionViewHolder.Money.setText((transactionItem.getMoney()));
    }

    @Override
    public int getItemCount() {
        return TransactionListItem.size();
    }

    // 
    class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView TransactionMessage, Datetime, Money;
        TransactionViewHolder(View itemView)
        {
            super(itemView);
            TransactionMessage = itemView.findViewById(R.id.h_message);
            Datetime = itemView.findViewById(R.id.h_datetime);
            Money = itemView.findViewById(R.id.h_money);
        }
    }

}
