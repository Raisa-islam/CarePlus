package com.raisa.update1.Card;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.Constants.ViewConstant;
import com.raisa.update1.R;
import com.raisa.update1.Views.DayViewCheckBox;
import com.raisa.update1.adapter.MemSelectAdapter;
import com.raisa.update1.adapter.TaskListAdapter;
import com.raisa.update1.object.Model;
import com.raisa.update1.object.Task;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class TaskList_card extends AppCompatActivity {
    TextView addTask;
    RecyclerView recyclerView, recyclerView2;
    TaskListAdapter adapter;
    ArrayList<Task> list;
    GifImageView noNote;

    ImageView select;

    TextView currentMember;
//    int l, m;
    private DatabaseReference rootRef, rootRef2;

    MemSelectAdapter adapter2;
    ArrayList<Model> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_task_list_card);


        addTask = findViewById(R.id.addTask);
        noNote = findViewById(R.id.noNote);
        //****************************************************
        recyclerView = findViewById(R.id.taskRecycler);

        select = findViewById(R.id.select);

        rootRef2 =  FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberList");

        recyclerView2 = findViewById(R.id.mem_item2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        list2 = new ArrayList<>();


        currentMember = findViewById(R.id.currentView);


        currentMember.setText(GlobalVariable.UserName);
        rootRef2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Model member = dataSnapshot.getValue(Model.class);
                    list2.add(member);
                }
                adapter2.notifyDataSetChanged();
                if(list2.size()==0){

                }
                else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adapter2 = new MemSelectAdapter(TaskList_card.this, list2);// rootref
        recyclerView2.setAdapter(adapter2);



        ViewConstant.UserNameOfView = GlobalVariable.UserName;
        ViewConstant.UidOfView = GlobalVariable.Uid;
        ViewConstant.EmailOfView = GlobalVariable.Email;

        showdata();

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdata();
                currentMember.setText(ViewConstant.UserNameOfView);
            }
        });





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
        Task task = new Task(id, title_add, description_add, hour, min, everyday, sun, mon, tues, wed, thurs, fri, sat);
        rootRef.child(id).setValue(task);

    }

    private void showdata()
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(ViewConstant.UidOfView).child("Tasks");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //****************************************************************************************************************************************************************************
        list = new ArrayList<>();

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

                }
                else{
                    noNote.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new TaskListAdapter(this, list);// rootref
        recyclerView.setAdapter(adapter);
    }
}