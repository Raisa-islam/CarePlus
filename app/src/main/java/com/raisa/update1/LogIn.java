package com.raisa.update1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {
    TextView createnewAccount;
    EditText email;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);
        createnewAccount=findViewById(R.id.createNewAccount);
        login = findViewById(R.id.btnLogin);
        email = findViewById(R.id.inputEmail);



        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,Register.class));
            }
        });

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(Integer.parseInt(email.getText().toString())==1)
               {
                   startActivity(new Intent(LogIn.this,MainActivity2.class));
               }
               else{
                   startActivity(new Intent(LogIn.this,MainActivity3.class));
               }

           }
       });



    }
}