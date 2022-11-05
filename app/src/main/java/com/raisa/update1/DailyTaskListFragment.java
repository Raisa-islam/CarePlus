package com.raisa.update1;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.raisa.update1.Card.TaskList_card;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.Constants.ViewConstant;
import com.raisa.update1.Views.DayViewCheckBox;
import com.raisa.update1.adapter.FragmentTaskListAdapter;
import com.raisa.update1.adapter.MemSelectAdapter;
import com.raisa.update1.object.Model;
import com.raisa.update1.object.Task;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class DailyTaskListFragment extends Fragment {
    TextView addTask;
    RecyclerView recyclerView, recyclerView2;
    FragmentTaskListAdapter adapter;
    ArrayList<Task> list;
    GifImageView noNote;
    ImageView self;
    int l, m;
    private DatabaseReference rootRef, rootRef2;

    ImageView select;

    TextView currentMember;
    //    int l, m;


    MemSelectAdapter adapter2;
    ArrayList<Model> list2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily_task_list,container,false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view,savedInstanceState);

        set_var();

        addTask =getView().findViewById(R.id.addTask);
        noNote = getView().findViewById(R.id.noNote);
        //****************************************************
        select = getView().findViewById(R.id.select);

        rootRef2 =  FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("MemberList");

        recyclerView2 = getView().findViewById(R.id.mem_item2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        list2 = new ArrayList<>();


        currentMember = getView().findViewById(R.id.currentView);
        self = getView().findViewById(R.id.self);

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


        adapter2 = new MemSelectAdapter(getContext(), list2);// rootref
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

    private void showCreateTaskeDialog() {


        final Dialog dialog = new Dialog(getContext());
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
                    Toast.makeText(getContext(), "Please fill all the fields...", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
                Toast.makeText(getContext(), "Task added...", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void add_data(String t, String d, String hrs, String min, String e, String sun1,
                          String mon1, String tues1, String wed1, String thurs1, String f, String sat1) {
        String id = rootRef.push().getKey();
        String description_add=d;
        String title_add=t;
        String hour=hrs;
        String everyday=e;
        String mon=mon1;
        String tues=tues1;
        String wed=wed1;
        String thurs=thurs1;
        String fri=f;
        String sat=sat1;
        String sun=sun1;
        Task task = new Task(id, title_add, description_add, hour, min, everyday, sun, mon, tues, wed, thurs, fri, sat);
        rootRef.child(id).setValue(task);
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

    private void showdata()
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(ViewConstant.UidOfView).child("Tasks");
        recyclerView = getView().findViewById(R.id.taskRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        //RecyclerView.LayoutManager LinearLayoutManager = null;
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        adapter = new FragmentTaskListAdapter(getContext(), list);// rootref
        recyclerView.setAdapter(adapter);
    }
}
