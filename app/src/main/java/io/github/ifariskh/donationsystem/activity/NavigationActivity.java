package io.github.ifariskh.donationsystem.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.fragment.CreditCardFragment;
import io.github.ifariskh.donationsystem.fragment.HomeFragment;
import io.github.ifariskh.donationsystem.fragment.SearchFragment;
import io.github.ifariskh.donationsystem.fragment.SettingFragment;
import io.github.ifariskh.donationsystem.helper.QuickPayDialog;
import io.github.ifariskh.donationsystem.helper.ResetPasswordDialog;

public class NavigationActivity extends AppCompatActivity {

    // Define global variable
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        floatingActionButton = findViewById(R.id.quick_pay);

        bottomNavigationView.setBackground(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.search:
                    fragment = new SearchFragment();
                    break;
                case R.id.card:
                    fragment = new CreditCardFragment();
                    break;
                case R.id.setting:
                    fragment = new SettingFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();

            return true;
        });

        floatingActionButton.setOnClickListener(view -> {
            new QuickPayDialog().show(getSupportFragmentManager(), "Quick Pay Dialog");
        });
    }


}