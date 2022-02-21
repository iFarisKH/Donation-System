package io.github.ifariskh.donationsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.helper.Constant;

public class OTPActivity extends AppCompatActivity {

    private String email, otp, msg;
    private EditText otp1, otp2, otp3, otp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
            msg = extras.getString("msg");
        }

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);

        sendOTP();
    }

    public void resendOTP(View view) {
        sendOTP();
    }

    public void verify(View view) {
        String inputOTP = otp1.getText().toString().trim() +
                otp2.getText().toString().trim() +
                otp3.getText().toString().trim() +
                otp4.getText().toString().trim();

        Intent intent;
        if (inputOTP.equals(otp)) {
            switch (msg) {
                case "login":
                    intent = new Intent(this, NavigationActivity.class);
                    startActivity(intent);
                    break;
                case "reset":
                    intent = new Intent(this, ResetPasswordActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void sendOTP() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            String getOTP = jObj.getString("msg");
                            Log.d("TAG", "onResponse: " + getOTP);
                            if (!getOTP.equalsIgnoreCase("error")) {
                                otp = getOTP;
                            } else {
                                Toast.makeText(getApplicationContext(), "Error: " + "wrong code", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("OTP", "Response: " + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                return map;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}