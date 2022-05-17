package io.github.ifariskh.donationsystem.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.ifariskh.donationsystem.activity.AdminActivity;
import io.github.ifariskh.donationsystem.activity.SignUpActivity;
import io.github.ifariskh.donationsystem.helper.Constant;

public class Admin extends User{

    public boolean validate(User user, Context context) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.UPDATE_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.isEmpty()) {
                                return;
                            }
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            String isValid = jsonObject.getString("msg");
                            Toast.makeText(context, "Response: " + isValid, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Admin", "Response: " + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", user.getId());
                return map;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
        return true;
    }
}
