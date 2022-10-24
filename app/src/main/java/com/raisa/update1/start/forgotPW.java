package com.raisa.update1.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.raisa.update1.R;

public class forgotPW extends AppCompatActivity {
    EditText email;
    TextView send;
    TextView gotoL;

    boolean valid = true;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_pw);

        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.inputEmailFpw);
        send = findViewById(R.id.Send);
        gotoL= findViewById(R.id.gotoLogin);

        gotoL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgotPW.this, LogIn.class));
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(email);
                if(valid){
                    forgotPassword();
                }

            }
        });

    }

    public boolean checkField(EditText textField){
        if (textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
    private void forgotPassword()
    {
        firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(forgotPW.this, LogIn.class));
                    finish();
                    Toast.makeText(forgotPW.this, "Check your email address...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(forgotPW.this, "Enter your correct email id...", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(forgotPW.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}