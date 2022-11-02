package com.raisa.update1.Card;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;
import com.raisa.update1.adapter.E_adapter;
import com.raisa.update1.object.Emergency_msg;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Emmergency extends AppCompatActivity {
    FloatingActionButton FAB;
    RecyclerView recyclerView;
    E_adapter adapter;
    ArrayList<Emergency_msg> list;
    EditText message;
    ImageView send, mic;
    EditText msg;
    private DatabaseReference rootRef, rootRef2;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    String s;


    //#0099CC
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_emmergency);

        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("EmmergencyMessage");
        rootRef2 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Update");

        FAB = findViewById(R.id.Floating_icon);
        message = findViewById(R.id.EDTMSG);
        send = findViewById(R.id.idSend);
        msg = findViewById(R.id.EDTMSG);
        mic = findViewById(R.id.Mic);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast
                            .makeText(Emmergency.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

//************************************************************************************************************************************************
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m = msg.getText().toString();
                String done = "!!! "+ GlobalVariable.UserName + " need " + m+"!!!";
                String id = rootRef.push().getKey();
                Emergency_msg update = new Emergency_msg(id, done);                      // new cls intentionally create kri nai
                rootRef2.child(id).setValue(update);
                Toast.makeText(Emmergency.this, "Emergency message sent", Toast.LENGTH_SHORT).show();
                msg.setText("");
            }
        });
        rootRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Emergency_msg msg = dataSnapshot.getValue(Emergency_msg.class);
                    list.add(msg);
                }
                adapter.notifyDataSetChanged();
                if(list.size()==0){

                }
                else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new E_adapter(this, list);
        recyclerView.setAdapter(adapter);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_dialog();
            }
        });
    }

    public void show_dialog()
    {
        final Dialog dialog = new Dialog(Emmergency.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);


        dialog.setContentView(R.layout.new_mesage_add);

        EditText title = dialog.findViewById(R.id.idEdtMSG);
        Button save = dialog.findViewById(R.id.idBtnSave);
        Button cancel = dialog.findViewById(R.id.idBtnCancel);




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = title.getText().toString().trim();


                if(!TextUtils.isEmpty(message))
                {

                    add_info(message);


                }
                else {

                }

                Toast.makeText(Emmergency.this, "Message Added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void add_info(String s)
    {
        String id = rootRef.push().getKey();
        Emergency_msg msg = new Emergency_msg(id, s);
        rootRef.child(id).setValue(msg);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                msg.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }

    }

}