package com.raisa.update1.Card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.raisa.update1.adapter.EventListAdapter;
import com.raisa.update1.adapter.MemSelectAdapter;
import com.raisa.update1.adapter.TaskListAdapter;
import com.raisa.update1.object.Event;
import com.raisa.update1.object.Model;
import com.raisa.update1.object.Task;

import java.util.ArrayList;

public class Calender_card extends AppCompatActivity {
    private DatabaseReference rootRef, rootRef2;
    TextView addEvent;
    ImageView calender;
    EventListAdapter adapter;
    ArrayList<Event> list;
    RecyclerView recyclerView;

    ImageView self;

    MemSelectAdapter adapter2;
    ArrayList<Model> list2;
    RecyclerView recyclerView2;
    ImageView select;

    TextView currentMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calender_card);



        addEvent = findViewById(R.id.addTask);
        calender = findViewById(R.id.calendar);

        select = findViewById(R.id.select);
        rootRef2 =  FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberList");

        recyclerView2 = findViewById(R.id.mem_item2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        list2 = new ArrayList<>();

        addEvent = findViewById(R.id.addTask);

        calender = findViewById(R.id.calendar);
        currentMember = findViewById(R.id.currentView);
        self = findViewById(R.id.self);

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMember.setText(GlobalVariable.UserName);
                ViewConstant.UserNameOfView = GlobalVariable.UserName;
                ViewConstant.UidOfView = GlobalVariable.Uid;
                ViewConstant.EmailOfView = GlobalVariable.Email;
                showdata();

            }
        });


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


        adapter2 = new MemSelectAdapter(Calender_card.this, list2);// rootref
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






        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender();
            }
        });
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateTaskeDialog();
            }
        });
    }

   private void showCreateTaskeDialog() {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.create_event);

       EditText title = dialog.findViewById(R.id.idEdtTask);
       EditText description = dialog.findViewById(R.id.idEdtTaskDescription);
        Button add = dialog.findViewById(R.id.addTask);

       TimePicker timePicker = dialog.findViewById(R.id.SelectTime);
       final int[] hour = new int[1];
       final int[] min = new int[1];
       final int[] year = new int[1];
       final int[] month = new int[1];
       final int[] day = new int[1];

       DatePicker datePicker = dialog.findViewById(R.id.SelectDate);

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

                    addInfo(t, d, da,m,y, h, m);
                }
                else {
                    Toast.makeText(Calender_card.this, "Please fill all the fields...", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });



        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    private void showCalender()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_calendar_view);

        dialog.show();


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation2;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void addInfo(String title, String description, String dd, String mm, String yy, String hour, String min)
    {
        String id = rootRef.push().getKey();
        Event event = new Event(id, title, description, hour, min, dd, mm, yy);
        rootRef.child(id).setValue(event);
    }

    public void showdata()
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("Events");
        recyclerView = findViewById(R.id.eventRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        rootRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Event task = dataSnapshot.getValue(Event.class);
                    list.add(task);
                }
                adapter.notifyDataSetChanged();
                if(list.size()==0){

                }
                else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new EventListAdapter(this, list);// rootref
        recyclerView.setAdapter(adapter);
    }
}