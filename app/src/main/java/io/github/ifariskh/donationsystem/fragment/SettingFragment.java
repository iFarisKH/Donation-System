package io.github.ifariskh.donationsystem.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.activity.KYCActivity;
import io.github.ifariskh.donationsystem.activity.SignInActivity;
import io.github.ifariskh.donationsystem.core.User;

public class SettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference kyc = findPreference("kyc");
        Preference signOut = findPreference("sign_out");

        kyc.setOnPreferenceClickListener(this);
        signOut.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        switch (preference.getKey()){
            case "kyc":
                Intent intent = new Intent(getActivity(), KYCActivity.class);
                startActivity(intent);
                break;
            case "sign_out":
                User.logout(getContext());
                break;
        }
        return true;
    }
}