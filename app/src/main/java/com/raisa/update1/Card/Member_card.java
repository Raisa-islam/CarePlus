package com.raisa.update1.Card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.MainActivity2;
import com.raisa.update1.R;
import com.raisa.update1.adapter.MemberListAdapter;
import com.raisa.update1.adapter.MemberReqAdapter;
import com.raisa.update1.adapter.TaskListAdapter;
import com.raisa.update1.object.Member;
import com.raisa.update1.object.Model;
import com.raisa.update1.object.Task;
import com.raisa.update1.start.LogIn;

import java.util.ArrayList;

public class Member_card extends AppCompatActivity {
    RecyclerView recyclerView;
    MemberListAdapter adapter;
    ArrayList<Model> list;

    private DatabaseReference rootRef;
    //they are for member list
    TextView Find;

    //Member requests
    RecyclerView recyclerViewReq;
    MemberReqAdapter adapterReq;
    ArrayList<Model> listReq;

    private DatabaseReference rootRefReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_member_card);

        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberList");
        //****************************************************
        recyclerView = findViewById(R.id.MemListRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        Find = findViewById(R.id.FindMemberPage);
        rootRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Model member = dataSnapshot.getValue(Model.class);
                    list.add(member);
                }
                adapter.notifyDataSetChanged();
                if(list.size()==0){

                }
                else{
                   // noNote.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new MemberListAdapter(this, list);// rootref
        recyclerView.setAdapter(adapter);

        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Member_card.this, FindMember.class);
                startActivity(i);
            }
        });

        rootRefReq = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberRequest");
        //****************************************************
        recyclerViewReq = findViewById(R.id.MemReqRV);
        recyclerViewReq.setHasFixedSize(true);
        recyclerViewReq.setLayoutManager(new LinearLayoutManager(this));
        listReq = new ArrayList<>();

        rootRefReq.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listReq.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Model member = dataSnapshot.getValue(Model.class);
                    listReq.add(member);
                }
                adapterReq.notifyDataSetChanged();
                if(listReq.size()==0){

                }
                else{
                    // noNote.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapterReq = new MemberReqAdapter(this, listReq);
        recyclerViewReq.setAdapter(adapterReq);
    }


}