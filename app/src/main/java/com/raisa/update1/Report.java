package com.raisa.update1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.raisa.update1.Constants.GlobalVariable;


public class Report extends AppCompatActivity {

    Button send;
    String email = "careplus683@gmail.com";
    String subjectcon = "Reporting about bug";
    String bodycon = "Hello Care Plus group\n"+"\n"+"\n" +GlobalVariable.UserName;
    TextView address;
    EditText subject, body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);

        send = findViewById(R.id.sendBtn);
        address = findViewById(R.id.emailAddress);
        address.setText(email);
        subject = findViewById(R.id.subject);
        subject.setText(subjectcon);
        body = findViewById(R.id.message);
        body.setText(bodycon);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String[] addresses = email.split(",");

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText());
                intent.putExtra(Intent.EXTRA_TEXT, body.getText());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    subject.setText(subjectcon);
                    body.setText(bodycon);

                } else {
                    Toast.makeText(Report.this, "No App is Installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}