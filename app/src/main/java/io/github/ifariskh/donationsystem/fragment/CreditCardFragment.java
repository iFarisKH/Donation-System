package io.github.ifariskh.donationsystem.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.activity.SignInActivity;
import io.github.ifariskh.donationsystem.core.CreditCard;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.core.User;
import io.github.ifariskh.donationsystem.helper.Constant;
import io.github.ifariskh.donationsystem.helper.CreditCardAdapter;
import io.github.ifariskh.donationsystem.helper.CreditCardDialog;

public class CreditCardFragment extends Fragment implements View.OnClickListener {

    private Button addCard;
    private TextView balance;
    private RecyclerView recyclerView;
    private CreditCardAdapter creditCardAdapter;

    ArrayList<CreditCard> creditCardList = new ArrayList<>();

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
        recyclerView = view.findViewById(R.id.credit_card);
        balance = view.findViewById(R.id.balance);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadCreditCards();
        getBalance();

    }

    private void getBalance() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.GET_BALANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            balance.setText(jsonObject.getString("balance"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SignIn", "Response: " + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", EndUser.ID);
                return map;
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

    private void loadCreditCards() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.GET_CREDIT_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.isEmpty()){
                                return;
                            }
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray creditCards = jsonObject.getJSONArray("cards");
                            for (int i = 0; i < creditCards.length(); i++) {
                                JSONObject creditCardObject = creditCards.getJSONObject(i);
                                String number = creditCardObject.getString("number");
                                number = "****      ****        ****        " + number.substring(number.length() - 4);
                                String name = creditCardObject.getString("name");
                                String cvc = creditCardObject.getString("cvc");
                                String exp = creditCardObject.getString("exp");
                                CreditCard creditCard = new CreditCard(number, cvc, name, exp);
                                creditCardList.add(creditCard);
                            }
                            creditCardAdapter = new CreditCardAdapter(getContext(), creditCardList);
                            recyclerView.setAdapter(creditCardAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SignIn", "Response: " + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", EndUser.ID);
                return map;
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

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