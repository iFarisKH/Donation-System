package io.github.ifariskh.donationsystem.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.regex.Pattern;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // Define global
    private TextInputLayout fullName, email, id, password, phone;
    private EditText dob;
    private Button signInBt, singUpBt;
    private User user;
    private RadioGroup radioGroup;
    private String type = "Donator";
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        dob = findViewById(R.id.dob);
        signInBt = findViewById(R.id.sign_in_button);
        singUpBt = findViewById(R.id.sign_up_button);
        radioGroup = findViewById(R.id.radio_group);

        initCalender();

        signInBt.setOnClickListener(this::onClick);
        singUpBt.setOnClickListener(this::onClick);
    }

    public void getUserType(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        type = radioButton.getText().toString();
    }

    private void initCalender() {
        final Calendar CALENDER = Calendar.getInstance();
        CALENDER.add(Calendar.YEAR, -18);
        final int YEAR = CALENDER.get(Calendar.YEAR);
        final int MONTH = CALENDER.get(Calendar.MONTH);
        final int DAY = CALENDER.get(Calendar.DAY_OF_MONTH);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                i1 = i1 + 1;
                                String date = i2 + "/" + i1 + "/" + i;
                                dob.setText(date);
                            }
                        }, YEAR, MONTH, DAY);
                datePickerDialog.getDatePicker().setMaxDate(CALENDER.getTime().getTime());
                datePickerDialog.show();
            }
        });
    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        fullName.setErrorEnabled(true);
        if (val.isEmpty()) {
            fullName.setError("*Required");
            return false;
        } else if (!Pattern.compile("^[ A-Za-z]{4,}+$").matcher(val).matches()) {
            fullName.setError("*Name should only have letters and space and at least 4 character");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
        }
        return true;
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

    private boolean validateId() {
        String val = id.getEditText().getText().toString().trim();
        id.setErrorEnabled(true);
        if (val.isEmpty()) {
            id.setError("*Required");
            return false;
        } else if (!Pattern.compile("^[0-9]{10}+$").matcher(val).matches()) {
            id.setError("*Invalid ID");
            return false;
        } else {
            id.setError(null);
            id.setErrorEnabled(false);
        }
        return true;
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

    private boolean validatePhone() {
        String val = phone.getEditText().getText().toString().trim();
        phone.setErrorEnabled(true);
        if (val.isEmpty()) {
            phone.setError("*Required");
            return false;
        } else if (!Pattern.compile("^[0-9]{9}+$").matcher(val).matches()) {
            phone.setError("*Invalid phone number should be 10 numbers");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDOB() {
        String val = dob.getText().toString().trim();
        if (val.isEmpty()) {
            dob.setError("");
            return false;
        } else {
            dob.setError(null, null);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_up_button:
                if (validateFullName() && validateEmail() && validateId() && validatePassword() && validatePhone() && validateDOB()) {
                    user = new User(
                            fullName.getEditText().getText().toString().trim(),
                            email.getEditText().getText().toString().trim(),
                            id.getEditText().getText().toString().trim(),
                            phone.getEditText().getText().toString().trim(),
                            dob.getText().toString().trim()
                    );
                    user.register(password.getEditText().getText().toString().trim(), this, email, id, type);
                }
                break;
        }
    }


}