package com.raisa.update1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogIn extends AppCompatActivity {
    TextView createnewAccount, fprgotPassward;
    EditText email, password;
    Button login;

    boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        createnewAccount=findViewById(R.id.createNewAccount);
        login = findViewById(R.id.btnLogin);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        fprgotPassward = findViewById(R.id.forgotPassword);



        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,Register.class));
            }
        });

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               /*if(Integer.parseInt(email.getText().toString())==1)
               {
                   startActivity(new Intent(LogIn.this,MainActivity2.class));
               }
               else{
                   startActivity(new Intent(LogIn.this,MainActivity3.class));
               }*/
                checkField(email);
                checkField(password);
               firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                   public void onSuccess(AuthResult authResult) {
                       Toast.makeText(LogIn.this, "Loggedin Successfully", Toast.LENGTH_SHORT).show();
                       checkUserAccessLevel(authResult.getUser().getUid());
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(LogIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });

        fprgotPassward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, forgotPW.class));
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

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData());

                if (documentSnapshot.getString("isAged") != null)
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                    finish();
                }
                if (documentSnapshot.getString("isCareGiver") != null)
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                    finish();
                }
                GlobalVariable.UserName = documentSnapshot.getString("Name");
                GlobalVariable.Email = documentSnapshot.getString("UserEmail");
                GlobalVariable.age = documentSnapshot.getString("Age");
                GlobalVariable.allergic = documentSnapshot.getString("Allergy");
                GlobalVariable.emergencyPerson = documentSnapshot.getString("EmmergencyPerson");
                GlobalVariable.healthIssue = documentSnapshot.getString("Health");
                GlobalVariable.others = documentSnapshot.getString("Others");
                GlobalVariable.contacNo = documentSnapshot.getString("emmergencyContact");
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isAged")!=null)
                    {
                        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        finish();
                    }
                    if (documentSnapshot.getString("isCareGiver")!=null)
                    {
                        startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                    finish();
                }
            });

        }
    }
}