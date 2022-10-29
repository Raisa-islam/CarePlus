package com.raisa.update1.Card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    private DatabaseReference rootRef;
    MemberSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_find_member);
        rootRef = FirebaseDatabase.getInstance().getReference().child("UserList");

        recyclerView = findViewById(R.id.recyclerViewSendreq);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();



        rootRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Model msg = dataSnapshot.getValue(Model.class);
                    if (msg.getEmail()==GlobalVariable.Email)
                    {
                        continue;
                    }
                    else
                    {
                        list.add(msg);
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

