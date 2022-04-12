package io.github.ifariskh.donationsystem.helper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;

public class QuickPayDialog extends AppCompatDialogFragment {

    private TextInputLayout amount;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_pay_distrebuted, null);

        amount = view.findViewById(R.id.amount);

        builder.setView(view)
                .setTitle("Distributed Pay")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        StringRequest stringRequest = new StringRequest(
                                Request.Method.POST,
                                Constant.PAY,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                            String isValid = jObj.getString("msg");
                                            switch (isValid) {
                                                case "success":
                                                    // TODO toast
                                                    break;

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Quick pay", "Response: " + error.toString());
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("donator", EndUser.ID);
                                map.put("amount", amount.getEditText().getText().toString());
                                map.put("type", "quick");
                                return map;
                            }
                        };

                        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

                    }
                });

        return builder.create();

    }

}
