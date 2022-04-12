package io.github.ifariskh.donationsystem.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.core.User;
import io.github.ifariskh.donationsystem.helper.Constant;

public class HomeFragment extends Fragment {

    private TextView name, indicator;
    private ImageView signout;
    private LinearProgressIndicator linearProgressIndicator;
    private int count;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        name = view.findViewById(R.id.name);
        indicator = view.findViewById(R.id.indicator);
        linearProgressIndicator = view.findViewById(R.id.progress);
        signout = view.findViewById(R.id.sign_out);
        getCount();
        name.setText(EndUser.Name);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndUser.logout(getContext());
            }
        });
        return view;
    }

    private void getCount() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.GET_COUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            count = jObj.getInt("msg");
                            double max = Math.ceil(count / 10.0);
                            max = max * 10;
                            linearProgressIndicator.setMax((int)(max));
                            linearProgressIndicator.setProgress(count);
                            indicator.setText(count +"/" + linearProgressIndicator.getMax());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Home Fragment", "Response: " + error.toString());
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
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}