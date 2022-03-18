package io.github.ifariskh.donationsystem.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.Transaction;

public class TransacitonAdapter extends RecyclerView.Adapter<TransacitonAdapter.TransacitonViewHolder> {

    private ArrayList<Transaction> transactionList;

    public TransacitonAdapter(ArrayList<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransacitonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_transaction, parent, false);
        return new TransacitonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransacitonViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.id.setText("Transaction ID: " + transaction.getId());
        holder.date.setText("Date: " + transaction.getDate());
        holder.amount.setText("Amount: " + transaction.getAmount() + " SAR");
        holder.from.setText(transaction.getDonatorId());
        holder.to.setText(transaction.getNeederId());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    class TransacitonViewHolder extends RecyclerView.ViewHolder {

        TextView id, date, amount, from, to;

        public TransacitonViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);

        }
    }
}
