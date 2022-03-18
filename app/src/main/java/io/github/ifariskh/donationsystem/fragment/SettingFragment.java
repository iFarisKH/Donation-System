package io.github.ifariskh.donationsystem.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.activity.KYCActivity;
import io.github.ifariskh.donationsystem.activity.TransacitonActivity;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.User;

public class SettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference kyc = findPreference("kyc");
        Preference signOut = findPreference("sign_out");
        Preference transaction = findPreference("transaction");

        if (EndUser.KYC.equals("No")) {
            kyc.setOnPreferenceClickListener(this);
        }
        signOut.setOnPreferenceClickListener(this);
        transaction.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        Intent intent;
        switch (preference.getKey()) {
            case "kyc":
                intent = new Intent(getActivity(), KYCActivity.class);
                startActivity(intent);
                break;
            case "sign_out":
                User.logout(getContext());
                break;
            case "transaction":
                intent = new Intent(getActivity(), TransacitonActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}