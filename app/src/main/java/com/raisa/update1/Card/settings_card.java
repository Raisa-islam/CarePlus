package com.raisa.update1.Card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;
import com.raisa.update1.Report;
import com.raisa.update1.TermsandPolicies;

public class settings_card extends AppCompatActivity {
    TextView terms;
    TextView report;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings_card);
        name = findViewById(R.id.usernameTextView);

        name.setText(GlobalVariable.UserName);

        terms = findViewById(R.id.terms);
        report = findViewById(R.id.report);

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( settings_card.this, TermsandPolicies.class);
                startActivity(i);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( settings_card.this, Report.class);
                startActivity(i);
            }
        });
    }
}