package io.github.ifariskh.donationsystem.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.core.Transaction;
import io.github.ifariskh.donationsystem.helper.AdminAdapter;
import io.github.ifariskh.donationsystem.helper.Constant;
import io.github.ifariskh.donationsystem.helper.TransacitonAdapter;

public class AdminActivity extends AppCompatActivity {

    // Define global variable
    private RecyclerView recyclerView;
    private AdminAdapter adminAdapter;
    private ArrayList<EndUser> endUsersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Init variables
        recyclerView = findViewById(R.id.recycle_view_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        loadKYC();

    }

    private void loadKYC() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constant.GET_KYC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Logging for debugging
                            Log.d("TAG", "onResponse: " + response);
                            if (response.isEmpty()) {
                                return;
                            }
                            // Substring JSON object
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray transactions = jsonObject.getJSONArray("kyc");
                            Log.d("TAG", "onResponse: " + transactions.length());
                            for (int i = 0; i < transactions.length(); i++) {
                                JSONObject creditCardObject = transactions.getJSONObject(i);
                                String id = creditCardObject.getString("id");
                                String name = creditCardObject.getString("name");
                                String dob = creditCardObject.getString("dob");
                                EndUser endUser = new EndUser(id, name, dob);
                                endUsersList.add(endUser);
                            }
                            // Init Adapter
                            adminAdapter = new AdminAdapter(endUsersList);
                            recyclerView.setAdapter(adminAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AdminActivity", "Response: " + error.toString());
            }
        }) {
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
