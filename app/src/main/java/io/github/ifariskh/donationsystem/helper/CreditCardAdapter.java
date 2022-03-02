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
import io.github.ifariskh.donationsystem.core.CreditCard;

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.CreditCardViewHolder> {

    private Context context;
    private ArrayList<CreditCard> creditCardList;

    public CreditCardAdapter(Context context, ArrayList<CreditCard> creditCardList) {
        this.context = context;
        this.creditCardList = creditCardList;
    }

    @NonNull
    @Override
    public CreditCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_recycle_card, null);
        return new CreditCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditCardViewHolder holder, int position) {
        CreditCard creditCard = creditCardList.get(position);

        holder.cardNum.setText("Card " + (position + 1));
        holder.number.setText(creditCard.getNumber());
        holder.expireDate.setText(creditCard.getExpireDate());
        holder.cvc.setText(creditCard.getCvc());
    }

    @Override
    public int getItemCount() {
        return creditCardList.size();
    }

    class CreditCardViewHolder extends RecyclerView.ViewHolder {

        TextView cardNum, number, expireDate, cvc;

        public CreditCardViewHolder(@NonNull View itemView) {
            super(itemView);

            cardNum = itemView.findViewById(R.id.num);
            number = itemView.findViewById(R.id.card_num);
            expireDate = itemView.findViewById(R.id.exp);
            cvc = itemView.findViewById(R.id.cvc);

        }
    }
}
