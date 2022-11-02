package com.raisa.update1.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
        SharedPreferences shrd = context.getSharedPreferences("Constants", MODE_PRIVATE);
        // pushing in member list
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(shrd.getString("uid", null)).child("MemberList");
        String id = rootRef.push().getKey();
        Model member = new Model(id, model.getKey(), model.getName(), model.getEmail());
        rootRef.child(id).setValue(member);
        //pushing in memberlist of other side
        rootRef3 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(model.getKey()).child("MemberList");
        String id2 = rootRef.push().getKey();
        Model member2 = new Model(id, shrd.getString("uid", null), shrd.getString("name","null"), shrd.getString("email", "null"));
        rootRef.child(id2).setValue(member2);
        //deleting  from request list
        rootRef2 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(shrd.getString("uid", null)).child("MemberRequest").child(model.getId());
        rootRef2.removeValue();
        Toast.makeText(context, "Request Accepted!", Toast.LENGTH_SHORT).show();
    }

    public void deleteReq(String key)
    {
        SharedPreferences shrd = context.getSharedPreferences("Constants", MODE_PRIVATE);
        rootRef2 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(shrd.getString("uid", null) ).child("MemberRequest").child(key);
        rootRef2.removeValue();
    }
}
