package io.github.ifariskh.donationsystem.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.helper.Constant;

public class NeederPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    TextView id, gender, socialStatus, salary, age;
    TextInputLayout amount;
    Button pay, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needer_payment);

        id = findViewById(R.id.id);
        gender = findViewById(R.id.gender);
        socialStatus = findViewById(R.id.ss);
        salary = findViewById(R.id.salary);
        age = findViewById(R.id.age);
        pay = findViewById(R.id.pay);
        cancel = findViewById(R.id.cancel);
        amount = findViewById(R.id.amount);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id.setText(extras.getString("id"));
            gender.setText(extras.getString("gender"));
            socialStatus.setText(extras.getString("socialStatus"));
            salary.setText(extras.getString("salary"));
            age.setText(extras.getString("age"));
        }
        Log.d("TAG", "onCreate: " + EndUser.ID);
        pay.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay:
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Processing...");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constant.PAY,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    Log.d("TAG", "onResponse: " + response);
                                    JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                    String isValid = jObj.getString("msg");
                                    switch (isValid) {
                                        case "success":
                                            Toast.makeText(getApplicationContext(), "Message: " + "success payment", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                        map.put("donator", EndUser.ID);
                        map.put("needer", id.getText().toString());
                        map.put("amount", amount.getEditText().getText().toString());
                        return map;
                    }
                };

                RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
                break;
            case R.id.cancel:
                break;
        }
    }
}