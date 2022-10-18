package com.raisa.update1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity2 extends AppCompatActivity {
    CardView task, basic_info, settings, log_out, member, location, calender, immergency;
    EditText name;

    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);

        task = findViewById(R.id.CardTask);
        basic_info = findViewById(R.id.card_Basic);
        settings = findViewById(R.id.Card_settings);
        log_out = findViewById(R.id.Card_Logout);
        member = findViewById(R.id.card_AddMember);
        name = findViewById(R.id.Edtusername);

        set_var();

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, TaskList_card.class);
                startActivity(i);
            }
        });

        basic_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, Basic_info_card.class);
                startActivity(i);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, settings_card.class);
                startActivity(i);
            }
        });
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, Member_card.class);
                startActivity(i);
            }
        });


        //********************************************************************************************************** logout relating to database
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity2.this, LogIn.class);
                startActivity(i);
                finish();
                Toast.makeText(MainActivity2.this, "Logged out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void set_var()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        GlobalVariable.Uid = user.getUid();


        DocumentReference df = fStore.collection("Users").document(GlobalVariable.Uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData());
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
        name.setText(GlobalVariable.UserName);

    }
}