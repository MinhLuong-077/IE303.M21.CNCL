package com.example.ewallet.model;
import java.util.List;

public class TransactionGroupItem {
    // declaration variables
    private String mYear;
    private List<TransactionItem> TransactionListItem;

    // constructor
    public TransactionGroupItem(String mYear, List<TransactionItem> TransactionListItem)
    {
        this.mYear = mYear;
        this.TransactionListItem = TransactionListItem;
    }

    // getter and setter
    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public List<TransactionItem> getTransactionListItem() {
        return TransactionListItem;
    }

    public void setTransactionListItem(List<TransactionItem> transactionListItem) {
        TransactionListItem = transactionListItem;
    }
}
