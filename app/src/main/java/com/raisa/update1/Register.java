package com.raisa.update1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    TextView alreadyHaveaccount;
    EditText Name, email, password, confirmPW;
    Button regBtn;
    CheckBox aged, caregiver;
    boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        alreadyHaveaccount=findViewById(R.id.alreadyHaveaccount);
        email = findViewById(R.id.inputEmail);
        Name = findViewById(R.id.inputUserName);
        password = findViewById(R.id.inputPassword);
        confirmPW = findViewById(R.id.inputConfirmPassword);
        aged = findViewById(R.id.CBaged);
        caregiver = findViewById(R.id.CBgiver);

        regBtn = findViewById(R.id.btnRegister);

        aged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    caregiver.setChecked(false);
                }
            }
        });

        caregiver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    aged.setChecked(false);
                }
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(Name);
                checkField(email);
                checkField(password);
                checkField(confirmPW);

                //****************************************
                if (!(aged.isChecked() || caregiver.isChecked()))
                {
                    Toast.makeText(Register.this, "Select Account Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                //****************************************************************************************************** password and confirm password check
                if(valid)
                {
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            DocumentReference df = firestore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Name", Name.getText().toString());
                            userInfo.put("UserEmail", email.getText().toString());
                            userInfo.put("Age", "");
                            userInfo.put("Allergy", "");
                            userInfo.put("EmmergencyPerson", "");
                            userInfo.put("EmmergencyPerson2", "");
                            userInfo.put("Health", "");
                            userInfo.put("Others", "");
                            userInfo.put("emmergencyContact", "");
                            userInfo.put("emmergencyContact2", "");
                            userInfo.put("emmergencyContactEmail", "");
                            userInfo.put("emmergencyContactEmail2", "");
                            userInfo.put("BloodGroup", "");

                            // specifying role
                            if (aged.isChecked())
                            {
                                userInfo.put("isAged", "1");
                            }
                            if (caregiver.isChecked()) {
                                userInfo.put("isCareGiver", "1");
                            }


                            df.set(userInfo);

                            if (aged.isChecked())
                            {
                                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                                finish();
                            }
                            if (caregiver.isChecked())
                            {
                                startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                                finish();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        alreadyHaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,LogIn.class));
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
}