package com.raisa.update1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raisa.update1.Views.DayViewCheckBox;

import java.util.ArrayList;

public class TaskList_card extends AppCompatActivity {
    TextView addTask;
    RecyclerView recyclerView;
    TaskListAdapter adapter;
    ArrayList<Task> list;
    int l, m;
    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_task_list_card);
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Tasks");

        addTask = findViewById(R.id.addTask);
        //****************************************************
        recyclerView = findViewById(R.id.taskRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //****************************************************************************************************************************************************************************
/*list = new ArrayList<>();
       adapter = new TaskListAdapter(this, list);// rootref
       recyclerView.setAdapter(adapter);
       rootRef.addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               list.clear();
               for (DataSnapshot dataSnapshot : snapshot.getChildren())
               {
                   Task task = dataSnapshot.getValue(Task.class);
                   list.add(task);
               }
               adapter.notifyDataSetChanged();
               if(list.size()==0){
                   //noTasks.setText("No Tasks to show");
               }
               else{
                  // noTasks.setText("");<pl.droidsonroids.gif.GifImageView
                   //                android:layout_width="300dp"
                   //                android:layout_height="300dp"
                   //                android:layout_gravity="center"
                   //                android:background="@drawable/no_note"
                   //                android:id="@+id/noNote"/>
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
*/

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateTaskeDialog();
            }
        });
    }

    private void showCreateTaskeDialog()
    {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_create_task);

        TextView createTask;
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

        TimePicker timePicker = dialog.findViewById(R.id.TimePicker);
        final int[] hour = new int[1];
        final int[] min = new int[1];

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
        //moner moto coding*******************************************
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
                    add_data(t, d, hrs, min, e, sun1, mon1, tues1, wed1, thurs1, f, sat1 );


                }
                else {
                    Toast.makeText(TaskList_card.this, "Please fill all the fields...", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
                Toast.makeText(TaskList_card.this, "Task added...", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    private void add_data(String title_add, String description_add, String hour, String min, String everyday, String sun, String mon, String tues,
                          String wed, String thurs, String fri, String sat)
    {
        String id = rootRef.push().getKey();
        Task task = new Task(title_add, description_add, hour, min, everyday, sun, mon, tues, wed, thurs, fri, sat);
        rootRef.child(id).setValue(task);

    }
}