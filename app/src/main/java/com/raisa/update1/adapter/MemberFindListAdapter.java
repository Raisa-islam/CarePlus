package com.raisa.update1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.raisa.update1.R;
import com.raisa.update1.object.Member;

import java.util.ArrayList;

public class MemberFindListAdapter extends RecyclerView.Adapter<MemberFindListAdapter.MyViewHolder>{
    Context context;

    ArrayList<Member> list;


    public MemberFindListAdapter(Context context, ArrayList<Member> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MemberFindListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.member_list, parent, false);

        return new MemberFindListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberFindListAdapter.MyViewHolder holder, int position) {
        Member member = list.get(position);
        holder.name.setText(member.getName());
        holder.email.setText(member.getEmail());
        holder.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(); // nijer uid o pass krt hbe
                Toast.makeText(context, "request sent part is yet to complete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, sendRequest;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.memberEmail);
            name = itemView.findViewById(R.id.memberName);
            sendRequest = itemView.findViewById(R.id.sendRequest);

        }
    }
    public void sendRequest(){

    }
}
