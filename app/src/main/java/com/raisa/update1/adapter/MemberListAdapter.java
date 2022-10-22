package com.raisa.update1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.raisa.update1.R;
import com.raisa.update1.object.Member;

import java.util.ArrayList;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyViewHolder>{
    Context context;

    ArrayList<Member> list;


    public MemberListAdapter(Context context, ArrayList<Member> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MemberListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.member_list, parent, false);

        return new MemberListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberListAdapter.MyViewHolder holder, int position) {
        Member member = list.get(position);
        holder.name.setText(member.getName());
        holder.email.setText(member.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, email;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.memberEmail);
            name = itemView.findViewById(R.id.memberName);

        }
    }

}
