package com.raisa.update1.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;
import com.raisa.update1.object.Model;


import java.util.ArrayList;


public class FragmentMemberReqAdapter extends RecyclerView.Adapter<FragmentMemberReqAdapter.MyViewHolder> {

    Context context;
    private DatabaseReference rootRef, rootRef2, rootRef3;

    ArrayList<Model> list;


    public FragmentMemberReqAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FragmentMemberReqAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.member_list_request, parent, false);

        return new FragmentMemberReqAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentMemberReqAdapter.MyViewHolder holder, int position) {
        Model member = list.get(position);
        holder.name.setText(member.getName());
        holder.email.setText(member.getEmail());
        holder.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptRequest(member);

            }
        });
        holder.deleteReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReq(member.getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, sendRequest, deleteReq;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.memberEmail);
            name = itemView.findViewById(R.id.memberName);
            sendRequest = itemView.findViewById(R.id.sendRequest);
            deleteReq = itemView.findViewById(R.id.DeleteRequest);

        }
    }
    public void AcceptRequest(Model model){

        set_var();
        // pushing in member list
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberList");
        String id = rootRef.push().getKey();
        Model member = new Model(id, model.getKey(), model.getName(), model.getEmail());
        rootRef.child(id).setValue(member);
        //pushing in memberlist of other side
        rootRef3 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(model.getKey()).child("MemberList");
        String id2 = rootRef3.push().getKey();
        Model member2 = new Model(id2, GlobalVariable.Uid,GlobalVariable.UserName, GlobalVariable.Email);
        rootRef3.child(id2).setValue(member2);
        //deleting  from request list
        rootRef2 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberRequest").child(model.getId());
        rootRef2.removeValue();
        Toast.makeText(context, "Request Accepted!", Toast.LENGTH_SHORT).show();
    }

    public void deleteReq(String key)
    {
        set_var();
        rootRef2 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberRequest").child(key);
        rootRef2.removeValue();
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
