package com.raisa.update1.adapter;

import android.content.Context;
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
import com.raisa.update1.object.Member;
import com.raisa.update1.object.Model;

import java.util.ArrayList;

public class MemberReqAdapter extends RecyclerView.Adapter<MemberReqAdapter.MyViewHolder>{
    Context context;
    private DatabaseReference rootRef, rootRef2, rootRef3;

    ArrayList<Model> list;


    public MemberReqAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MemberReqAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.member_list_request, parent, false);

        return new MemberReqAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberReqAdapter.MyViewHolder holder, int position) {
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

        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberList");
        String id = rootRef.push().getKey();
        Model member = new Model(id, model.getKey(), model.getName(), model.getEmail());
        rootRef.child(id).setValue(member);

        rootRef3 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(model.getKey()).child("MemberList");
        String id2 = rootRef3.push().getKey();
        Model member2 = new Model(id2, GlobalVariable.Uid, GlobalVariable.UserName, GlobalVariable.Email);
        rootRef3.child(id2).setValue(member2);

        rootRef2 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberRequest").child(model.getId());
        rootRef2.removeValue();
        Toast.makeText(context, "Request Accepted!", Toast.LENGTH_SHORT).show();
    }

    public void deleteReq(String key)
    {
        rootRef2 = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberRequest").child(key);
        rootRef2.removeValue();
    }
}
