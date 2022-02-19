package io.github.ifariskh.donationsystem.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.helper.Constant;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputLayout password;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
        }

        password = findViewById(R.id.password);
    }

    public void update(View view) {
        if (validatePassword()){
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constant.UPDATE_PASSWORD,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                String getMsg = jObj.getString("msg");
                               switch (getMsg){
                                   case "1":
                                       Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                       startActivity(intent);
                                       break;
                                   case "0":
                                       Toast.makeText(getApplicationContext(), "Error: " + "cannot update the password", Toast.LENGTH_LONG).show();
                                       break;
                               }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Reset Password", "Response: " + error.toString());
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("email", email);
                    map.put("password", password.getEditText().getText().toString().trim());
                    return map;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        password.setErrorEnabled(true);
        if (val.isEmpty()) {
            password.setError("*Required");
            return false;
        } else if (!Pattern.compile(
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,16}$")
                .matcher(val).matches()) {
            password.setError("Password must contain at least one digit [0-9].\n" +
                    "Password must contain at least one lowercase Latin character [a-z].\n" +
                    "Password must contain at least one uppercase Latin character [A-Z].\n" +
                    "Password must contain at least one special character like ! @ # & ( ).\n" +
                    "Password must contain a length of at least 8 characters and a maximum of 20 characters.");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
        }
        return true;
    }
}