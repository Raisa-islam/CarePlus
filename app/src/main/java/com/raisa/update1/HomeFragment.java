package com.raisa.update1;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raisa.update1.adapter.FragmentHomeAdapter;
import com.raisa.update1.adapter.FragmentTaskListAdapter;
import com.raisa.update1.object.Member;
import com.raisa.update1.object.Model;
import com.raisa.update1.object.Task;
import com.raisa.update1.object.UpdateObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    FragmentHomeAdapter adapter;
    ArrayList<UpdateObject> list;

    private DatabaseReference rootRef, rootRef2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        SharedPreferences shrd = getContext().getSharedPreferences("Constants", MODE_PRIVATE);

        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(shrd.getString("uid", null)).child("MemberList");

        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();

        rootRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Model member = dataSnapshot.getValue(Model.class);

                    rootRef2 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(member.getKey()).child("Update");
                    rootRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                            {
                                UpdateObject update = dataSnapshot1.getValue(UpdateObject.class);
                                list.add(update);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //UpdateObject task = dataSnapshot.getValue(UpdateObject.class);
                   // list.add(task);
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new FragmentHomeAdapter(getContext(), list);// rootref
        recyclerView.setAdapter(adapter);
    }

// if(list.size()==0){
//
//    }
//                else{
//        //noNote.setVisibility(View.GONE);
//    }

}
