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
    EditText name, age, allergy, EPerson, health, others, contact, bg, EPerson2, contact2, mail1, mail2;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_basic_info_card);

        edit_info = findViewById(R.id.edit_info);
       // info = findViewById(R.id.info_text);
        Name = findViewById(R.id.TVname);
        email = findViewById(R.id.TVEmail);

        Name.setText(GlobalVariable.UserName);
        email.setText(GlobalVariable.Email);

        name = findViewById(R.id.hintName);
        age = findViewById(R.id.hintAge);
        bg = findViewById(R.id.hintBloodGroup);
        allergy = findViewById(R.id.hintAllergicTo);
        EPerson = findViewById(R.id.hintName1);
        EPerson2 = findViewById(R.id.hintName2);

        health = findViewById(R.id.hintHealthIssues);
        others = findViewById(R.id.Others);
        contact = findViewById(R.id.hintMobileNo);
        contact2 = findViewById(R.id.hintMobileNo2);
        mail1 = findViewById(R.id.Mail);
        mail2 = findViewById(R.id.Mail3);


        set_data();//Change hbe ei function*************************************************************8

        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditInfoDialog();
                set_data();
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
        EditText name, age, health, allergy, ePerson, eContact, others, bloodGroup, email1, email2, ePerson2, econtact2;
        name = dialog.findViewById(R.id.idEdtName);
        age = dialog.findViewById(R.id.idEdtAge);
        health = dialog.findViewById(R.id.idEdtHealth);
        allergy = dialog.findViewById(R.id.idEdtAllergic);
        ePerson = dialog.findViewById(R.id.idEdtClose);
        eContact = dialog.findViewById(R.id.idEdtContact);
        others = dialog.findViewById(R.id.idEdtOthers);
        bloodGroup = dialog.findViewById(R.id.idEdtBg);
        email1 = dialog.findViewById(R.id.idEdtEmail);
        email2 = dialog.findViewById(R.id.idEdtEmail2);
        ePerson2 = dialog.findViewById(R.id.idEdtClose2);
        econtact2 = dialog.findViewById(R.id.idEdtContact2);


        dialog.show();
        name.setText(GlobalVariable.UserName);
        age.setText(GlobalVariable.age);
        health.setText(GlobalVariable.healthIssue);
        allergy.setText(GlobalVariable.allergic);
        ePerson.setText(GlobalVariable.emergencyPerson);
        eContact.setText(contacNo);
        others.setText(GlobalVariable.others);
        ePerson2.setText(GlobalVariable.emergencyPerson2);
        econtact2.setText(GlobalVariable.contacNo2);
        email1.setText(GlobalVariable.Email1);
        email2.setText(GlobalVariable.Email2);
        bloodGroup.setText(GlobalVariable.bg);
       save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> userInfo = new HashMap<>();
//
                userInfo.put("Name", name.getText().toString());
                //userInfo.put("UserEmail", email.getText().toString());
                userInfo.put("Age", age.getText().toString());
                userInfo.put("Allergy", allergy.getText().toString());
                userInfo.put("EmmergencyPerson", ePerson.getText().toString());
                userInfo.put("EmmergencyPerson2", ePerson2.getText().toString());
                userInfo.put("Health", health.getText().toString());
                userInfo.put("Others", others.getText().toString());
                userInfo.put("emmergencyContact", eContact.getText().toString());
                userInfo.put("emmergencyContact2", econtact2.getText().toString());
                userInfo.put("emmergencyContactEmail", email1.getText().toString());
                userInfo.put("emmergencyContactEmail2", email2.getText().toString());
                userInfo.put("BloodGroup", bloodGroup.getText().toString());

                db.collection("Users").whereEqualTo("UserEmail",GlobalVariable.Email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty())
                        {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentId = documentSnapshot.getId();
                            db.collection("Users").document(documentId)
                                    .update(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            GlobalVariable.age = age.getText().toString();
                                            GlobalVariable.healthIssue = health.getText().toString();
                                            GlobalVariable.allergic = allergy.getText().toString();
                                             GlobalVariable.emergencyPerson = EPerson.getText().toString();
                                             GlobalVariable.contacNo = eContact.getText().toString();
                                             GlobalVariable.others = others.getText().toString();
                                             GlobalVariable.bg = bloodGroup.getText().toString();
                                             GlobalVariable.emergencyPerson = ePerson.getText().toString();
                                             GlobalVariable.emergencyPerson2 = ePerson2.getText().toString();
                                             GlobalVariable.contacNo2 = econtact2.getText().toString();
                                             GlobalVariable.Email1 = email1.getText().toString();
                                             GlobalVariable.Email2 = email2.getText().toString();

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

    void set_data()
    {
        name.setText(GlobalVariable.UserName);
        age.setText(GlobalVariable.age);
        allergy.setText(GlobalVariable.allergic);
        EPerson.setText(GlobalVariable.emergencyPerson);
        EPerson2.setText(GlobalVariable.emergencyPerson2);
        health.setText(GlobalVariable.healthIssue);
        others.setText(GlobalVariable.others);
        bg.setText(GlobalVariable.bg);
        contact.setText(contacNo);
        contact2.setText(GlobalVariable.contacNo2);
        mail1.setText(GlobalVariable.Email1);
        mail2.setText(GlobalVariable.Email2);

    }
}