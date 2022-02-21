package io.github.ifariskh.donationsystem.helper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

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
import io.github.ifariskh.donationsystem.activity.OTPActivity;
import io.github.ifariskh.donationsystem.core.RequestHandler;

public class ResetPasswordDialog extends AppCompatDialogFragment {

    private TextInputLayout email;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_email, null);

        email = view.findViewById(R.id.email);

        builder.setView(view)
                .setTitle("Reset Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();

    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positive = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);

            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateEmail()) {
                        String val = email.getEditText().getText().toString().trim();

                        StringRequest stringRequest = new StringRequest(
                                Request.Method.POST,
                                Constant.FIND_EMAIL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                            String isValid = jObj.getString("msg");
                                            switch (isValid) {
                                                case "1":
                                                    Intent intent = new Intent(getContext(), OTPActivity.class);
                                                    intent.putExtra("email", val);
                                                    intent.putExtra("msg", "reset");
                                                    startActivity(intent);
                                                    break;
                                                case "0":
                                                    email.setErrorEnabled(true);
                                                    email.setError("*Email does not exist");
                                                    break;
                                            }
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
                                map.put("email", val);
                                return map;
                            }
                        };
                        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
                    }
                }
            });
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        email.setErrorEnabled(true);
        if (val.isEmpty()) {
            email.setError("*Required");
            return false;
        } else if (!Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(val).matches()) {
            email.setError("*Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
        }
        return true;
    }

}
