package com.raisa.update1.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

public class FragmentMemberListAdapter extends RecyclerView.Adapter<FragmentMemberListAdapter.MyViewHolder>  {
    Context context;
    private DatabaseReference rootRef;
    ArrayList<Model> list;


    public FragmentMemberListAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FragmentMemberListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.member_list, parent, false);

        return new FragmentMemberListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentMemberListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Model member = list.get(position);
        holder.name.setText(member.getName());
        holder.email.setText(member.getEmail());
        holder.unf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
                alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDeleteMember).
                        setPositiveButton(R.string.yes, (dialog, which) -> {
                            deleteMember(member.getId());
                        })
                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, unf;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.memberEmail);
            name = itemView.findViewById(R.id.memberName);
            unf = itemView.findViewById(R.id.unFriend);

        }
    }

    public void deleteMember(String key)
    {
        SharedPreferences shrd = context.getSharedPreferences("Constants", MODE_PRIVATE);
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(shrd.getString("uid","null")).child("MemberList").child(key);
        rootRef.removeValue();
        Toast.makeText(context, "Member Removed!", Toast.LENGTH_SHORT).show();
    }

}
