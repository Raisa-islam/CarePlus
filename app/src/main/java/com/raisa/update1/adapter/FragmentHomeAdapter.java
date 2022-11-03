package com.raisa.update1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;

import com.raisa.update1.object.UpdateObject;

import java.util.ArrayList;

public class FragmentHomeAdapter extends RecyclerView.Adapter<FragmentHomeAdapter.MyViewHolder> {

    Context context;

    ArrayList<UpdateObject> list;
    private DatabaseReference rootRef1, rootRefMembers;

    public FragmentHomeAdapter(Context context, ArrayList<UpdateObject> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public FragmentHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false);

        return new FragmentHomeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentHomeAdapter.MyViewHolder holder, int position) {
        UpdateObject task = list.get(position);
        holder.update.setText(task.getUpdate_msg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView update;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            update = itemView.findViewById(R.id.HomeUpdate);



        }
    }


}
