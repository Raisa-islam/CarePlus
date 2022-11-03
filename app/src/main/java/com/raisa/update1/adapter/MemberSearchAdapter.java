package com.raisa.update1.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.raisa.update1.Card.FindMember;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.object.Member;
import com.raisa.update1.object.Model;
import com.raisa.update1.R;

import java.util.ArrayList;
import java.util.List;

public class MemberSearchAdapter extends RecyclerView.Adapter<MemberSearchAdapter.MyViewHolder>{

    Context context;
    private DatabaseReference rootRef;

    ArrayList<Model> list;

    public MemberSearchAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MemberSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.send_request, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model member = list.get(position);
        holder.name.setText(member.getName());
        holder.email.setText(member.getEmail());
        holder.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(member); // nijer uid o pass krt hbe

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, sendRequest;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.memberEmail);
            name = itemView.findViewById(R.id.memberName);
            sendRequest = itemView.findViewById(R.id.sendRequest);

        }
    }

    public void sendRequest(Model model)
    {
        set_var();
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(model.getKey()).child("MemberRequest");
        String id = rootRef.push().getKey();

        Model member = new Model(id, GlobalVariable.Uid, GlobalVariable.UserName, GlobalVariable.Email);
        rootRef.child(id).setValue(member);
        Toast.makeText(context, "Request send!", Toast.LENGTH_SHORT).show();
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

}
