package com.raisa.update1;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.raisa.update1.Card.FindMember;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.adapter.FragmentMemberListAdapter;
import com.raisa.update1.adapter.FragmentMemberReqAdapter;

import com.raisa.update1.object.Model;


import java.util.ArrayList;


public class AddMemberFragment extends Fragment {
    RecyclerView recyclerView;
    FragmentMemberListAdapter adapter;
    ArrayList<Model> list;

    private DatabaseReference rootRef;
    TextView Find;
    RecyclerView recyclerViewReq;
    FragmentMemberReqAdapter adapterReq;
    ArrayList<Model> listReq;

    private DatabaseReference rootRefReq;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_member,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
           super.onViewCreated(view, savedInstanceState);


           Find = getView().findViewById(R.id.FindMemberPage);
           set_var();


        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberList");
        //****************************************************
        recyclerView =getView().findViewById(R.id.MemListRV);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        Find = getView().findViewById(R.id.FindMemberPage);
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


        adapter = new FragmentMemberListAdapter(getContext(), list);// rootref
        recyclerView.setAdapter(adapter);


        Find.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent i = new Intent( getContext(), FindMember.class);
                   startActivity(i);
               }
        });

        rootRefReq = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberRequest");
        //****************************************************
        recyclerViewReq = getView().findViewById(R.id.MemReqRV);
        recyclerViewReq.setHasFixedSize(true);
        //********************************
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext());
        recyclerViewReq.setLayoutManager(linearLayoutManager1);
        listReq = new ArrayList<>();

        rootRefReq.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listReq.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Model member = dataSnapshot.getValue(Model.class);
                    Log.d("Requests", "here it is:"+member.getName());
                    listReq.add(member);
                }
                adapter.notifyDataSetChanged();
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
        adapterReq = new FragmentMemberReqAdapter(getContext(), listReq);
        Log.d("Request Adapter","onFailure: " + listReq.size() );
        recyclerViewReq.setAdapter(adapterReq);
    }

    void set_var()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore fStore;
        fStore = FirebaseFirestore.getInstance();
        GlobalVariable.Uid = user.getUid();


        DocumentReference df = fStore.collection("Users").document(GlobalVariable.Uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData());
                GlobalVariable.UserName = documentSnapshot.getString("Name");
                GlobalVariable.Email = documentSnapshot.getString("UserEmail");

            }
        });

    }
}
