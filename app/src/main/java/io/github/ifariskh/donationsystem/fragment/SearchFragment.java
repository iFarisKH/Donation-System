package io.github.ifariskh.donationsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import io.github.ifariskh.donationsystem.activity.NeederPaymentActivity;
import io.github.ifariskh.donationsystem.activity.OTPActivity;
import io.github.ifariskh.donationsystem.activity.SignInActivity;
import io.github.ifariskh.donationsystem.core.CreditCard;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.helper.Constant;
import io.github.ifariskh.donationsystem.helper.CreditCardAdapter;
import io.github.ifariskh.donationsystem.helper.SearchAdapter;

public class SearchFragment extends Fragment implements SearchAdapter.ItemClickListener{

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    ArrayList<EndUser> endUsersList = new ArrayList<>();

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initRecycleView(view);
        return view;
    }

    private void initRecycleView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadNeeders();
    }

    private void loadNeeders() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constant.GET_NEEDERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.isEmpty()){
                                return;
                            }
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray creditCards = jsonObject.getJSONArray("needers");
                            for (int i = 0; i < creditCards.length(); i++) {
                                JSONObject creditCardObject = creditCards.getJSONObject(i);
                                String id = creditCardObject.getString("id");
                                String gender = creditCardObject.getString("gender");
                                String salary = creditCardObject.getString("salary");
                                String dob = creditCardObject.getString("dob");
                                String socialStatus = creditCardObject.getString("socialStatus");
                                EndUser endUser = new EndUser(id, gender, socialStatus, Double.parseDouble(salary), dob);
                                endUsersList.add(endUser);
                            }
                            addNedeer();
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
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void addNedeer(){
        searchAdapter = new SearchAdapter(getContext(), endUsersList, this::onItemClick);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onItemClick(EndUser endUser) {
        Intent intent = new Intent(getContext(), NeederPaymentActivity.class);
        intent.putExtra("id", endUser.getId());
        intent.putExtra("gender", endUser.getGender());
        intent.putExtra("socialStatus", endUser.getSocialStatus());
        intent.putExtra("salary", endUser.getSalary() + "");
        intent.putExtra("age", endUser.getDob());
        startActivity(intent);
    }
}