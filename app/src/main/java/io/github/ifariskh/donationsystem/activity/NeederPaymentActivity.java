package io.github.ifariskh.donationsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import io.github.ifariskh.donationsystem.R;

public class NeederPaymentActivity extends AppCompatActivity {

    TextView id, gender, socialStatus, salary, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needer_payment);

        id = findViewById(R.id.id);
        gender = findViewById(R.id.gender);
        socialStatus = findViewById(R.id.ss);
        salary = findViewById(R.id.salary);
        age = findViewById(R.id.age);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id.setText(extras.getString("id"));
            gender.setText(extras.getString("gender"));
            socialStatus.setText(extras.getString("socialStatus"));
            salary.setText(extras.getString("salary"));
            age.setText(extras.getString("age"));
        }
    }
}