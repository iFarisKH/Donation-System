package io.github.ifariskh.donationsystem.core;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.github.ifariskh.donationsystem.activity.SignUpActivity;
import io.github.ifariskh.donationsystem.helper.Constant;

public class User {
    private String fullName;
    private String email;
    private String id;
    private String phone;
    private String dob;

    public User(String fullName, String email, String id, String phone, String dob) {
        this.fullName = fullName;
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.dob = dob;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void register(String password, Context ctx, TextInputLayout eEmail, TextInputLayout eId, String type) {
        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Signing up");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SignUp", "Response: " + response);
                        progressDialog.dismiss();
                        try{
                            JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            String msg = jObj.getString("msg");
                            switch (msg){
                                case "Id":
                                    eEmail.setError(null);
                                    eEmail.setErrorEnabled(false);
                                    eId.setErrorEnabled(true);
                                    eId.setError("Id already registered");
                                    break;
                                case "Email":
                                    eId.setError(null);
                                    eId.setErrorEnabled(false);
                                    eEmail.setErrorEnabled(true);
                                    eEmail.setError("Email already registered");
                                    break;
                                default:
                                    eEmail.setError(null);
                                    eId.setError(null);
                                    eEmail.setErrorEnabled(false);
                                    eId.setErrorEnabled(false);
                                    Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ctx, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SignUp", "Response: " + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("fullname", fullName);
                map.put("email", email);
                map.put("password", password);
                map.put("phone", phone);
                map.put("dob", dob);
                map.put("type", type);
                return map;
            }
        };

        RequestHandler.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
