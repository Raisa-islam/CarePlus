package com.raisa.update1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {
    Context context;

    ArrayList<Task> list;


    public TaskListAdapter(Context context, ArrayList<Task> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_task_card, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = list.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.hour.setText(task.getHour());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, description, hour, min, days, status;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            TextView hour = itemView.findViewById(R.id.hour);
            //TextView days = itemView.findViewById(R.id.days);
           // TextView status = itemView.findViewById(R.id.status);


            //age = itemView.findViewById(R.id.Age);
        }
    }

}
