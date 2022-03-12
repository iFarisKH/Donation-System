package io.github.ifariskh.donationsystem.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.core.User;
import io.github.ifariskh.donationsystem.helper.Constant;
import io.github.ifariskh.donationsystem.helper.ResetPasswordDialog;

public class SignInActivity extends AppCompatActivity {

    private Button signUpBt, signInBt, forget;
    private TextInputLayout email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUpBt = findViewById(R.id.sign_up_button);
        signInBt = findViewById(R.id.sign_in_button);
        forget = findViewById(R.id.forget);
        email = findViewById(R.id.user);
        password = findViewById(R.id.password);

        signUpBt.setOnClickListener(view -> {
            openSignUpActivity();
        });

        signInBt.setOnClickListener(view -> {
            validate();
        });

        forget.setOnClickListener(view -> {
            openEmailDialog();
        });
    }

    private void openEmailDialog() {
        ResetPasswordDialog resetPasswordDialog = new ResetPasswordDialog();
        resetPasswordDialog.show(getSupportFragmentManager(), "Reset Password Dialog");
    }

    private void validate() {
        String tEmail = email.getEditText().getText().toString().trim();
        String tPassword = password.getEditText().getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Signing in");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            String isValid = jObj.getString("msg");
                            switch (isValid) {
                                case "invalid":
                                    Toast.makeText(getApplicationContext(), "Error: " + "wrong sign up information", Toast.LENGTH_LONG).show();
                                    break;
                                case "inactive":
                                    Toast.makeText(getApplicationContext(), "Error: " + "the email hasn't verified", Toast.LENGTH_LONG).show();
                                    break;
                                case "session":
                                    Toast.makeText(getApplicationContext(), "Error: " + "another device using this account", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    String[] res = isValid.split(" ");
                                    User.TYPE = res[1];
                                    if (!User.TYPE.equals("Admin")){
                                        EndUser.ID = res[0];
                                        EndUser.KYC = res[2];
                                    }
                                    openNavigationActivity();
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
                map.put("email", tEmail);
                map.put("password", tPassword);
                return map;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void openNavigationActivity() {
        Intent intent = new Intent(this, OTPActivity.class);
        intent.putExtra("email", email.getEditText().getText().toString().trim());
        intent.putExtra("msg", "login");
        startActivity(intent);
    }

    private void openSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


}