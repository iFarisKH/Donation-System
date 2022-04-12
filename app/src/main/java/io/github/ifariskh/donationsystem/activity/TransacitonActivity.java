package io.github.ifariskh.donationsystem.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.core.Transaction;
import io.github.ifariskh.donationsystem.helper.Constant;
import io.github.ifariskh.donationsystem.helper.TransacitonAdapter;

public class TransacitonActivity extends AppCompatActivity {

    // Define global variable
    private RecyclerView recyclerView;
    private TransacitonAdapter transacitonAdapter;

    ArrayList<Transaction> transactionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaciton);

        recyclerView = findViewById(R.id.recycle_view_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        loadTransaction();
    }

    private void loadTransaction() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.GET_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.isEmpty()) {
                                return;
                            }
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray transactions = jsonObject.getJSONArray("transactions");
                            Log.d("TAG", "onResponse: " + transactions.length());
                            for (int i = 0; i < transactions.length(); i++) {
                                JSONObject creditCardObject = transactions.getJSONObject(i);
                                String id = creditCardObject.getString("id");
                                String date = creditCardObject.getString("date");
                                String amount = creditCardObject.getString("amount");
                                String doantor = creditCardObject.getString("donator");
                                String needer = creditCardObject.getString("needer");
                                Transaction transaction = new Transaction(id, Float.valueOf(amount), doantor, needer, date);
                                transactionList.add(transaction);
                            }
                            transacitonAdapter = new TransacitonAdapter(transactionList);
                            recyclerView.setAdapter(transacitonAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Transaction Activity", "Response: " + error.toString());
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
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
