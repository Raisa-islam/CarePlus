package com.raisa.update1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Basic_info_card extends AppCompatActivity {
    ImageView edit_info;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info_card);

        edit_info = findViewById(R.id.edit_info);
        info = findViewById(R.id.info_text);

        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setText("eta niye kaj krt hbe... just database e info set" +
                        "and show here in the text box");
            }
        });
    }
}