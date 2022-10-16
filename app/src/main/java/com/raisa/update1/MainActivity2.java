package com.raisa.update1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity2 extends AppCompatActivity {
    CardView task, basic_info, settings, log_out, member, location, calender, immergency;
    EditText name;

    FirebaseFirestore db;
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
        name.setText(GlobalVariable.UserName);

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
}