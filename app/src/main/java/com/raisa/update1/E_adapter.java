package com.raisa.update1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class E_adapter extends RecyclerView.Adapter<E_adapter.MyViewHolder> {
        Context context;

        ArrayList<Emergency_msg> list;


public E_adapter(Context context, ArrayList<Emergency_msg> list) {
        this.context = context;
        this.list = list;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.messge_item, parent, false);

        return new MyViewHolder(v);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Emergency_msg task = list.get(position);
        holder.title.setText(task.getMsg());

}

@Override
public int getItemCount() {
        return list.size();
        }

public static class MyViewHolder extends RecyclerView.ViewHolder{

    TextView title;

    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        title = itemView.findViewById(R.id.title);

    }
}

}
