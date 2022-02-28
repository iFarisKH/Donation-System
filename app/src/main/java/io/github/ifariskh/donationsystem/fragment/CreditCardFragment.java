package io.github.ifariskh.donationsystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.helper.CreditCardDialog;

public class CreditCardFragment extends Fragment implements View.OnClickListener {

    private Button addCard;

    public CreditCardFragment() {
    }

    public static CreditCardFragment newInstance() {
        CreditCardFragment fragment = new CreditCardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_card, container, false);
        initViews(view);
        addCard.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        addCard = view.findViewById(R.id.add_card);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_card:
                new CreditCardDialog().show(getParentFragmentManager(), "Credit Card Dialog");
                break;
        }
    }
}