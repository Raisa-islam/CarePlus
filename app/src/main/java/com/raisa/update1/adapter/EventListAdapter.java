package com.raisa.update1.adapter;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.raisa.update1.Card.Calender_card;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.R;
import com.raisa.update1.object.Alarm;
import com.raisa.update1.object.Emergency_msg;
import com.raisa.update1.object.Event;
import com.raisa.update1.object.Task;
import com.raisa.update1.reminderset.AlarmBrodcast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder>{
    Context context;

    ArrayList<Event> list;
    private DatabaseReference rootRef;
    String timeTonotify;

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
    public void onBindViewHolder(@NonNull EventListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Event task = list.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.hour.setText(task.getHour());
        holder.min.setText(task.getMin());
       // holder.days.setText(task.getDay());
        holder.date.setText(task.getDd());
        holder.days.setText(task.getYyyy());
//        String mon = "";
//        if (task.getMm()=="1")
//        {
//            mon += "Jan";
//        }
//        if (task.getMm()=="2")
//        {
//            mon += "Feb";
//        }
//        if (task.getMm()=="3")
//        {
//            mon += "Mar";
//        }
//        if (task.getMm()=="4")
//        {
//            mon += "Apr";
//        }
//        if (task.getMm()=="5")
//        {
//            mon += "May";
//        }
//        if (task.getMm()=="6")
//        {
//            mon += "Jun";
//        }
//        if (task.getMm()=="7")
//        {
//            mon += "Jul";
//        }
//        if (task.getMm()=="8")
//        {
//            mon += "Aug";
//        }
//        if (task.getMm()=="9")
//        {
//            mon += "Sep";
//        }
//        if (task.getMm()=="10")
//        {
//            mon += "Oct";
//        }
//        if (task.getMm()=="11")
//        {
//            mon += "Nov";
//        }
//        if (task.getMm()=="12")
//        {
//            mon += "Dec";
//        }
        holder.month.setText(task.getMm());//***************************************************month kn show kre na


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
                               showUpdateTaskeDialog(task);

                                break;
                            case R.id.menuComplete:
                                AlertDialog.Builder completeAlertDialog = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
                                completeAlertDialog.setTitle(R.string.confirmation).setMessage(R.string.sureToMarkAsComplete).
                                        setPositiveButton(R.string.yes, (dialog, which) ->
                                                showCompleteDialog(task.getId(), task.getTitle(), position))
                                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();

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

                        Toast.makeText(context, "Event Reminder is set", Toast.LENGTH_SHORT).show();                                                     // alarm set krbo
                    }
                    else
                    {
                        Toast.makeText(context, "Event reminder is cancel", Toast.LENGTH_SHORT).show();
                    }


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
        Switch alarmStarted;

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

            alarmStarted = itemView.findViewById(R.id.item_alarm_started);




        }
    }
    private void showUpdateTaskeDialog(Event event) {


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.create_event);

        EditText title = dialog.findViewById(R.id.idEdtTask);
        EditText description = dialog.findViewById(R.id.idEdtTaskDescription);
        Button add = dialog.findViewById(R.id.addTask);

        TimePicker timePicker = dialog.findViewById(R.id.SelectTime);
        DatePicker datePicker = dialog.findViewById(R.id.SelectDate);
        final int[] hour = new int[1];
        final int[] min = new int[1];
        final int[] year = new int[1];
        final int[] month = new int[1];
        final int[] day = new int[1];

        title.setText(event.getTitle());
        description.setText(event.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setMinute(Integer.parseInt(event.getMin()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(Integer.parseInt(event.getHour()));
        }
        //**************************************************************************** kivabe date picker e date set krb

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                hour[0] = i;
                min[0] = i1;


            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    year[0]=i;
                    month[0] = i1+1;
                    day[0] = i2;
                }
            });
        }


        dialog.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = title.getText().toString().trim();
                String d = description.getText().toString().trim();

                String y = Integer.toString(year[0]);
                String m = Integer.toString(month[0]);
                String da = Integer.toString(day[0]);

                String h = Integer.toString(hour[0]);
                String mi = Integer.toString(min[0]);

                if(!TextUtils.isEmpty(t)||!TextUtils.isEmpty(d) )
                {

//
                    update(event.getId(), t, d,da, m, y,h, mi);
                }
                else {
                    Toast.makeText(context, "Please fill all the fields...", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });



        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    public void showCompleteDialog(String taskId, String taskTitle, int position) {
        Dialog dialog = new Dialog(context, R.style.AppTheme_2);
        dialog.setContentView(R.layout.dialog_completed_theme);
        Button close = dialog.findViewById(R.id.closeButton);
        close.setOnClickListener(view -> {
            //deleteTaskFromId(taskId, position);
            Update_in_fb(taskTitle);
            delete(taskId);
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }


    private void update(String id, String title, String description, String dd, String mm, String yy, String hour, String min)
    {
        set_var();

        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Events");
        //rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Tasks");
        Event event = new Event(id, title, description, hour, min, dd, mm, yy);
        rootRef.child(id).setValue(event);
        Toast.makeText(context, "Event updated", Toast.LENGTH_SHORT).show();

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
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Events").child(key);
        rootRef.removeValue();
        Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show();
    }

    void set_var()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore fStore;
        fStore = FirebaseFirestore.getInstance();
        GlobalVariable.Uid = user.getUid();


        DocumentReference df = fStore.collection("Users").document(GlobalVariable.Uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData());
                GlobalVariable.UserName = documentSnapshot.getString("Name");
                GlobalVariable.Email = documentSnapshot.getString("UserEmail");

            }
        });

    }

//    private void setAlarm(String text, String date, String time) {
//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigining alaram manager object to set alaram
//
//        Intent intent = new Intent(context.getApplicationContext(), AlarmBrodcast.class);
//        intent.putExtra("event", text);                                                       //sending data to alarm class to create channel and notification
//        intent.putExtra("time", date);
//        intent.putExtra("date", time);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        String dateandtime = date + " " + timeTonotify;
//        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
//        try {
//            Date date1 = formatter.parse(dateandtime);
//            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
//            Toast.makeText(context.getApplicationContext(), "Alaram", Toast.LENGTH_SHORT).show();
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Intent intentBack = new Intent(context.getApplicationContext(), Calender_card.class);                //this intent will be called once the setting alaram is completes
//        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intentBack);                                                                  //navigates from adding reminder activity ot mainactivity
//
//    }
//
//    private Object getSystemService(String alarmService) {
//    }
}
