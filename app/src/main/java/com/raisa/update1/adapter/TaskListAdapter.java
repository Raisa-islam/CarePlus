package com.raisa.update1.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.AlarmClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;
import com.raisa.update1.Views.DayViewCheckBox;
import com.raisa.update1.object.Alarm;
import com.raisa.update1.object.Emergency_msg;
import com.raisa.update1.object.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {
    Context context;
    final ArrayList<Integer> daysOfWeek= new ArrayList<>();

    ArrayList<Task> list;
    private DatabaseReference rootRef;

    String m = "";

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Task task = list.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.hour.setText(task.getHour());
        holder.min.setText(task.getMin());

      if(task.getEveryday() == "1")
       {
           daysOfWeek.add(Calendar.SUNDAY);
           daysOfWeek.add(Calendar.MONDAY);
           daysOfWeek.add(Calendar.TUESDAY);
           daysOfWeek.add(Calendar.WEDNESDAY);
           daysOfWeek.add(Calendar.THURSDAY);
           daysOfWeek.add(Calendar.FRIDAY);
           daysOfWeek.add(Calendar.SATURDAY);
           m = "Everyday";
       }
        if (task.getSun() == "1")
       {
           daysOfWeek.add(Calendar.SUNDAY);
           String n = "Sun ";
           m += n;
       }
       if (task.getMon()=="1")
       {
           daysOfWeek.add(Calendar.MONDAY);
           String n = "Mon ";
            m += n;
       }
        if (task.getTues() == "1")
        {
            daysOfWeek.add(Calendar.TUESDAY);
            String n = "Tues ";
            m += n;
        }
        if (task.getWed() == "1")
        {
            daysOfWeek.add(Calendar.WEDNESDAY);
            String n = "Wed ";
            m += n;
        }
        if (task.getThurs() == "1")
        {
            daysOfWeek.add(Calendar.THURSDAY);
            String n = "Thurs ";
            m += n;
        }
        if (task.getFri() == "1")
        {
            daysOfWeek.add(Calendar.FRIDAY);
            String n = "Fri ";
            m += n;
        }
        if (task.getSat() == "1")
        {
            daysOfWeek.add(Calendar.SATURDAY);
            String n = "Sat ";
            m += n;
        }
        holder.days.setText(m);
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
                                alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDelete).
                                        setPositiveButton(R.string.yes, (dialog, which) -> {
                                            deleteTaskFromId(task.getId(), position);
                                        })
                                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();


                                break;
                            case R.id.menuUpdate:
                                showUpdateDialog(task);

                                break;
                            case R.id.menuComplete:
                                AlertDialog.Builder completeAlertDialog = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
                                completeAlertDialog.setTitle(R.string.confirmation).setMessage(R.string.sureToMarkAsComplete).
                                        setPositiveButton(R.string.yes, (dialog, which) ->
                                                showCompleteDialog(task.getId(), task.getTitle(), position))
                                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();
                              //  Toast.makeText(context, "Complete pressed Inner part is remain", Toast.LENGTH_SHORT).show();
                                holder.status.setText("COMPLETED");

                                break;
                        }
                        return false;
                    }
                });
            }
        });

        holder.alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("alarm", "state " + b);
                if(b)
                {
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(task.getHour()));
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(task.getMin()));
                    intent.putExtra(AlarmClock.EXTRA_DAYS, daysOfWeek);
                    intent.putExtra(AlarmClock.EXTRA_VIBRATE, false);
                    intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                    intent.putExtra(AlarmClock.EXTRA_RINGTONE, true);
                    if(intent.resolveActivity(context.getPackageManager())!=null)
                    {
                        holder.rootView.getContext().startActivity(intent);
                    }
                    Toast.makeText(context, "alarm is set", Toast.LENGTH_SHORT).show();                                                     // alarm set krbo
                }
                else
                {
                    Toast.makeText(context, "alarm is cancel", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, description, hour, min, status, days;
        ImageView menu;
        View rootView;
        Switch alarmStarted;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            rootView = itemView;
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            hour = itemView.findViewById(R.id.hour);
            min = itemView.findViewById(R.id.min);
            menu = itemView.findViewById(R.id.options);
            status = itemView.findViewById(R.id.status);
            alarmStarted = itemView.findViewById(R.id.item_alarm_started);


             days = itemView.findViewById(R.id.days);
            // TextView status = itemView.findViewById(R.id.status);


            //age = itemView.findViewById(R.id.Age);
        }
    }

    private void showUpdateDialog(Task task)
    {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_create_task);

        Button createTask;
        createTask = dialog.findViewById(R.id.AddNewTask);

        EditText title_f = dialog.findViewById(R.id.idEdtTask);
        EditText Task_desc = dialog.findViewById(R.id.idEdtTaskDescription);

        CheckBox everyDay = dialog.findViewById(R.id.every_day);
        DayViewCheckBox sun = dialog.findViewById(R.id.dv_sunday);
        DayViewCheckBox mon = dialog.findViewById(R.id.dv_monday);
        DayViewCheckBox tues = dialog.findViewById(R.id.dv_tuesday);
        DayViewCheckBox wed = dialog.findViewById(R.id.dv_wednesday);
        DayViewCheckBox thurs = dialog.findViewById(R.id.dv_thursday);
        DayViewCheckBox fri = dialog.findViewById(R.id.dv_friday);
        DayViewCheckBox sat = dialog.findViewById(R.id.dv_saturday);
        createTask.setText("Update");
        TimePicker timePicker = dialog.findViewById(R.id.TimePicker);
        final int[] hour = new int[1];
        final int[] min = new int[1];
        title_f.setText(task.getTitle());
        Task_desc.setText(task.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(Integer.parseInt(task.getHour()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setMinute(Integer.parseInt(task.getMin()));
        }
        if(task.getEveryday() =="1")
        {
            everyDay.setChecked(true);
        }
        if(task.getSat() =="1")
        {
            sat.setChecked(true);
        }
        if(task.getSun() =="1")
        {
            sun.setChecked(true);
        }
        if(task.getMon() =="1")
        {
            mon.setChecked(true);
        }
        if(task.getTues() =="1")
        {
            tues.setChecked(true);
        }
        if(task.getWed() =="1")
        {
            wed.setChecked(true);
        }
        if(task.getThurs() =="1")
        {
            thurs.setChecked(true);
        }
        if(task.getFri() =="1")
        {
            fri.setChecked(true);
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                hour[0] = i;
                min[0] = i1;


            }
        });

        everyDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    sat.setChecked(false);
                    sun.setChecked(false);
                    mon.setChecked(false);
                    tues.setChecked(false);
                    wed.setChecked(false);
                    thurs.setChecked(false);
                    fri.setChecked(false);
                }
            }
        });

        sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                everyDay.setChecked(false);
            }
        });
        sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                everyDay.setChecked(false);
            }
        });
        mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                everyDay.setChecked(false);
            }
        });
        tues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                everyDay.setChecked(false);
            }
        });
        wed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                everyDay.setChecked(false);
            }
        });
        thurs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                everyDay.setChecked(false);
            }
        });
        fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                everyDay.setChecked(false);
            }
        });

        dialog.show();

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = title_f.getText().toString().trim();
                String d = Task_desc.getText().toString().trim();
                int n = hour[0];
                int m = min[0];
                String hrs = Integer.toString(n);
                String min = Integer.toString(m);
                boolean ad = false;
                String e="0", sun1="0", mon1= "0", tues1 = "0", wed1="0", thurs1="0", f="0", sat1="0";
                if (everyDay.isChecked()) {
                    ad = true;
                    e ="1";
                }
                if (sat.isChecked()) {
                    ad = true;
                    sat1 ="1";
                }
                if (sun.isChecked()) {
                    ad = true;
                    sun1 ="1";
                }
                if (mon.isChecked()) {
                    ad = true;
                    mon1 ="1";
                }
                if (tues.isChecked()) {
                    ad = true;
                    tues1 ="1";
                }
                if (wed.isChecked()) {
                    ad = true;
                    wed1 ="1";
                }
                if (thurs.isChecked()) {
                    ad = true;
                    thurs1="1";
                }
                if (fri.isChecked())
                {
                    ad = true;
                    f = "1";
                }
                if(!TextUtils.isEmpty(t)||!TextUtils.isEmpty(d) || ad)
                {

                    update(task.getId(), t, d,hrs, min, e, sun1, mon1, tues1, wed1, thurs1,f, sat1);

                }
                else {

                }


                dialog.dismiss();

            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    private void update(String id, String title, String description, String hour, String min,
                        String everyday, String sun, String mon, String tues, String wed, String thurs, String fri, String sat)
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Tasks");
        Task task = new Task(id, title, description, hour, min, everyday, sun, mon, tues, wed, thurs, fri, sat);
        rootRef.child(id).setValue(task);
        Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show();

    }
    public void showCompleteDialog(String taskId, String taskTitle, int position) {
        Dialog dialog = new Dialog(context, R.style.AppTheme_2);
        dialog.setContentView(R.layout.dialog_completed_theme);
        Button close = dialog.findViewById(R.id.closeButton);
        close.setOnClickListener(view -> {
            //deleteTaskFromId(taskId, position);
            Update_in_fb(taskTitle);
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
    public void Update_in_fb(String title)
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Update");
        String done = GlobalVariable.UserName + " have completed " + title+"!";
        String id = rootRef.push().getKey();
        Emergency_msg update = new Emergency_msg(id, done);                                                                       // new cls intentionally create kri nai
        rootRef.child(id).setValue(update);
    }
    public void  deleteTaskFromId(String id, int position)
    {
        delete(id);

    }
    private void delete(String key)
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Tasks").child(key);
        rootRef.removeValue();
        Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
    }
}
