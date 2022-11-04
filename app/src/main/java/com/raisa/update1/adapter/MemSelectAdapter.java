package com.raisa.update1.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
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
import com.raisa.update1.Constants.ViewConstant;
import com.raisa.update1.R;
import com.raisa.update1.object.Emergency_msg;
import com.raisa.update1.object.Model;

import java.util.ArrayList;

public class MemSelectAdapter  extends RecyclerView.Adapter<MemSelectAdapter.MyViewHolder>{
    Context context;
    private DatabaseReference rootRef, rootRef2;
    static ArrayList<Model> list;


    public MemSelectAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MemSelectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mem_item, parent, false);

        return new MemSelectAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemSelectAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Model member = list.get(position);
        holder.name.setText(member.getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.memberName);


        }

        @Override
        public void onClick(View view) {
            int pos = this.getAdapterPosition();
            Model task = list.get(pos);

            Log.d("click", "clicked" + task.getName());
            ViewConstant.UidOfView = task.getKey();
            ViewConstant.EmailOfView = task.getEmail();
            ViewConstant.UserNameOfView = task.getName();

        }
    }


}
