package com.raisa.update1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.raisa.update1.Card.FindMember;
import com.raisa.update1.Constants.GlobalVariable;

public class SettingsFragment extends Fragment {
    TextView terms;
    TextView report;
    EditText name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings,container,false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        terms = getView().findViewById(R.id.terms);
        report = getView().findViewById(R.id.report);
        name = getView().findViewById(R.id.usernameTextView);
        name.setText(GlobalVariable.UserName);

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( getContext(), TermsandPolicies.class);
                startActivity(i);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( getContext(), Report.class);
                startActivity(i);
            }
        });
    }
}
