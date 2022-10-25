package com.raisa.update1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;
import com.raisa.update1.object.Event;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder>{
    Context context;

    ArrayList<Event> list;
    private DatabaseReference rootRef;

    public EventListAdapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_event_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.MyViewHolder holder, int position) {
        Event task = list.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.hour.setText(task.getHour());
        holder.min.setText(task.getMin());
       // holder.days.setText(task.getDay());
        holder.date.setText(task.getDd());
        String mon = "";
        if (task.getMm()=="1")
        {
            mon += "Jan";
        }
        if (task.getMm()=="2")
        {
            mon += "Feb";
        }
        if (task.getMm()=="3")
        {
            mon += "Mar";
        }
        if (task.getMm()=="4")
        {
            mon += "Apr";
        }
        if (task.getMm()=="5")
        {
            mon += "May";
        }
        if (task.getMm()=="6")
        {
            mon += "Jun";
        }
        if (task.getMm()=="7")
        {
            mon += "Jul";
        }
        if (task.getMm()=="8")
        {
            mon += "Aug";
        }
        if (task.getMm()=="9")
        {
            mon += "Sep";
        }
        if (task.getMm()=="10")
        {
            mon += "Oct";
        }
        if (task.getMm()=="11")
        {
            mon += "Nov";
        }
        if (task.getMm()=="12")
        {
            mon += "Dec";
        }
        holder.month.setText(mon);


        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.menuDelete:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
                                alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDeleteEvent).
                                        setPositiveButton(R.string.yes, (dialog, which) -> {
                                            deleteTaskFromId(task.getId(), position);
                                        })
                                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();


                                break;
                            case R.id.menuUpdate:
                                //showUpdateDialog(task);

                                break;
                            case R.id.menuComplete:
                                Toast.makeText(context, "Complete pressed Inner part is remain", Toast.LENGTH_SHORT).show();
                                holder.status.setText("COMPLETED");
                                break;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, description, hour, min, status, days, date, month;
        ImageView menu;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            hour = itemView.findViewById(R.id.hour);
            min = itemView.findViewById(R.id.min);
            menu = itemView.findViewById(R.id.options);
            days = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            month = itemView.findViewById(R.id.month);
            status = itemView.findViewById(R.id.status);



        }
    }

    public void  deleteTaskFromId(String id, int position)
    {
        delete(id);

    }
    private void delete(String key)
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Events").child(key);
        rootRef.removeValue();
        Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
    }
}
