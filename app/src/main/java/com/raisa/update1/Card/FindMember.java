package com.raisa.update1.Card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;
import com.raisa.update1.adapter.E_adapter;
import com.raisa.update1.adapter.EventListAdapter;
import com.raisa.update1.adapter.MemberSearchAdapter;
import com.raisa.update1.object.Emergency_msg;
import com.raisa.update1.object.Event;
import com.raisa.update1.object.Model;

import java.util.ArrayList;
import java.util.List;


public class FindMember extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Model> list;
    ArrayList<Model> listReq;
    ArrayList<Model> listMember;
    private DatabaseReference rootRef, rootRefReq, rootRefList;
    MemberSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_find_member);
        rootRef = FirebaseDatabase.getInstance().getReference().child("UserList");
        SharedPreferences shrd1 = getSharedPreferences("Constants", MODE_PRIVATE);
        Log.d("email",  "raisa_mentally_unstable"+shrd1.getString("email","null"));
        Log.d("uid",  "raisa_mentally_unstable"+shrd1.getString("uid","null"));
        rootRefReq = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(shrd1.getString("uid", "null")).child("MemberRequest");
        rootRefList =  FirebaseDatabase.getInstance().getReference().child("careNeeder").child(shrd1.getString("uid", "null")).child("MemberList");
        recyclerView = findViewById(R.id.recyclerViewSendreq);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        listReq = new ArrayList<>();
        listMember = new ArrayList<>();

        rootRefList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model msg3 = dataSnapshot.getValue(Model.class);
                    listReq.add(msg3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rootRefReq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model msg2 = dataSnapshot.getValue(Model.class);
                    listMember.add(msg2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SharedPreferences shrd = getSharedPreferences("Constants", MODE_PRIVATE);
        rootRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    Model msg = dataSnapshot.getValue(Model.class);
                    Log.d("care_plus", msg.getEmail() + " "+ shrd.getString("email","null"));
                    if (msg.getEmail().compareTo(shrd.getString("email","null"))==0)   //msg.getEmail()==GlobalVariable.Email
                    {
                        //Log.d("care_plus", msg.getEmail() + " "+ GlobalVariable.Email);
                        continue;
                    }
                    else {
                        list.add(msg);
                       /* */


                    }
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new MemberSearchAdapter(this, list);// rootref
        recyclerView.setAdapter(adapter);

    }
}

