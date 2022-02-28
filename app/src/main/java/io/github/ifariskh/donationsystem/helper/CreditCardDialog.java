package io.github.ifariskh.donationsystem.helper;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import io.github.ifariskh.donationsystem.R;

public class CreditCardDialog extends AppCompatDialogFragment implements TextWatcher {

    private HashMap<String, String> patternMap = null;
    private TextInputLayout cardNum;

    private ArrayList<Integer> BIN_LIST = new ArrayList<>(Arrays.asList(
            549760, 417633, 588982, 588845, 588846, 588848,
            588849, 588850, 535989, 535825, 521076, 536023,
            457865, 589206, 589005, 537767, 458456, 419593,
            524130, 524514, 484783, 431361, 445564, 539931,
            486094, 446404, 446393, 432328, 407197, 446672,
            605141, 604906, 513213, 409201, 434107, 489318,
            462220, 554180, 422819, 422817, 422818, 529741,
            529415, 439954, 410685, 543357, 543085, 557606,
            504300, 558848, 530906, 428671, 428331, 585265,
            532013, 531095, 508160, 440647, 440795, 440533,
            468540, 493428, 455708, 520058, 401757, 455036,
            636120, 968201, 968202, 968203, 968204, 968205,
            968206, 968207, 968208, 968209, 968210, 968211
    ));

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity(), R.style.MaterialAlertDialog_rounded);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_credit_card, null);

        if (patternMap == null) {
            patternMap = new HashMap<>();
            patternMap.put("VISA", Constant.VISA);
            patternMap.put("MASTERCARD", Constant.MASTERCARD);
        }

        cardNum = view.findViewById(R.id.number);

        cardNum.getEditText().addTextChangedListener(this);

        builder.setView(view);

        return builder.create();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String str = charSequence.toString();
        Log.d("TAG", "onTextChanged: " + str + " " + count);

        if (str.length() >= 6 && BIN_LIST.contains(Integer.parseInt(str.substring(0, 6)))) {
            cardNum.getEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mada, 0);
        } else if (str.matches(patternMap.get("VISA"))) {
            cardNum.getEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visa, 0);
        } else if (str.matches(patternMap.get("MASTERCARD"))) {
            Log.d("TAG", "onTextChanged: ");
            cardNum.getEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mastercard, 0);
        } else {
            cardNum.getEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
