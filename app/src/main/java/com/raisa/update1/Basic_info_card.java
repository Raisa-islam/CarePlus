package com.raisa.update1;

import static com.raisa.update1.GlobalVariable.contacNo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Basic_info_card extends AppCompatActivity {
    ImageView edit_info;
    TextView info;
    EditText Name, email;
    EditText name, age, allergy, EPerson, health, others, contact;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_basic_info_card);

        edit_info = findViewById(R.id.edit_info);
        info = findViewById(R.id.info_text);
        Name = findViewById(R.id.TVname);
        email = findViewById(R.id.TVEmail);

        Name.setText(GlobalVariable.UserName);
        email.setText(GlobalVariable.Email);
        name = findViewById(R.id.EdtN);
        age = findViewById(R.id.EdtAge);
        allergy = findViewById(R.id.EdtAllergy);
        EPerson = findViewById(R.id.Edtimmergency);
        health = findViewById(R.id.EdtHealthisuue);
        others = findViewById(R.id.EdtimmergencyOthers);
        contact = findViewById(R.id.EdtimmergencyContact);


        name.setText(GlobalVariable.UserName);
        age.setText(GlobalVariable.age);
        allergy.setText(GlobalVariable.allergic);
        EPerson.setText(GlobalVariable.emergencyPerson);
        health.setText(GlobalVariable.healthIssue);
        others.setText(GlobalVariable.others);
        contact.setText(contacNo);


        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditInfoDialog();
            }
        });
    }
    private void showEditInfoDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_change_basic_info);

        db = FirebaseFirestore.getInstance();

        TextView save = dialog.findViewById(R.id.SaveInfo);
        EditText name, age, health, allergy, ePerson, eContact, others;
        name = dialog.findViewById(R.id.idEdtName);
        age = dialog.findViewById(R.id.idEdtAge);
        health = dialog.findViewById(R.id.idEdtHealth);
        allergy = dialog.findViewById(R.id.idEdtAllergic);
        ePerson = dialog.findViewById(R.id.idEdtClose);
        eContact = dialog.findViewById(R.id.idEdtContact);
        others = dialog.findViewById(R.id.idEdtOthers);

        //moner moto coding*******************************************
        dialog.show();
        name.setText(GlobalVariable.UserName);
        age.setText(GlobalVariable.age);
        health.setText(GlobalVariable.healthIssue);
        allergy.setText(GlobalVariable.allergic);
        ePerson.setText(GlobalVariable.emergencyPerson);
        eContact.setText(contacNo);
        others.setText(GlobalVariable.others);
       save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> userDetail = new HashMap<>();
                userDetail.put("Name", GlobalVariable.UserName);
                userDetail.put("Age", age.getText().toString());
                userDetail.put("Health", health.getText().toString());
                userDetail.put("Allergy", allergy.getText().toString());
                userDetail.put("EmmergencyPerson", ePerson.getText().toString());
                userDetail.put("emmergencyContact", eContact.getText().toString());
                userDetail.put("Others", others.getText().toString());

                db.collection("Users").whereEqualTo("UserEmail",GlobalVariable.Email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty())
                        {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentId = documentSnapshot.getId();
                            db.collection("Users").document(documentId)
                                    .update(userDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            GlobalVariable.age = age.getText().toString();
                                            GlobalVariable.healthIssue = health.getText().toString();
                                            GlobalVariable.allergic = allergy.getText().toString();
                                             GlobalVariable.emergencyPerson = ePerson.getText().toString();
                                             GlobalVariable.contacNo = eContact.getText().toString();
                                             GlobalVariable.others = others.getText().toString();

                                            Toast.makeText(Basic_info_card.this, "Basic info edited...", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Basic_info_card.this, "Some Error Occured...", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(Basic_info_card.this, "Failed...", Toast.LENGTH_SHORT).show();
                        }
}               });
                dialog.dismiss();

            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}