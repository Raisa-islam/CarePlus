package com.raisa.update1.Card;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;

public class settings_card extends AppCompatActivity {
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings_card);
        name = findViewById(R.id.usernameTextView);

        name.setText(GlobalVariable.UserName);
    }
}