package com.raisa.update1.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.Constants.ViewConstant;
import com.raisa.update1.R;
import com.raisa.update1.object.Emergency_msg;

import java.util.ArrayList;

public class E_adapter extends RecyclerView.Adapter<E_adapter.MyViewHolder> {
        Context context;

        static ArrayList<Emergency_msg> list;


    private DatabaseReference rootRef;


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
        holder.menu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.member_menu, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId())
                    {
                        case R.id.menuDelete:
                            delete(task.getId());
                            break;
                        case R.id.menuUpdate:
                            showUpdateDialog(task);
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

public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    EditText title;
    ImageView menu;

    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        title = itemView.findViewById(R.id.title);
        menu = itemView.findViewById(R.id.options);


    }

    @Override
    public void onClick(View view) {
        int pos = this.getAdapterPosition();
        Emergency_msg task = list.get(pos);

        Log.d("click", "clicked" + task.getMsg());

    }
}
    private void delete(String key)
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("EmmergencyMessage").child(key);
        rootRef.removeValue();
        Toast.makeText(context, "Draft deleted", Toast.LENGTH_SHORT).show();
    }

    private void showUpdateDialog(Emergency_msg task){
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);


        dialog.setContentView(R.layout.new_mesage_add);

        EditText title = dialog.findViewById(R.id.idEdtMSG);
        Button save = dialog.findViewById(R.id.idBtnSave);
        Button cancel = dialog.findViewById(R.id.idBtnCancel);


        title.setText(task.getMsg());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = title.getText().toString().trim();


                if(!TextUtils.isEmpty(message))
                {
                    update(task.getId(), message);
                }
                else {

                }


                dialog.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void update(String id, String msg)
    {
        rootRef = FirebaseDatabase.getInstance().getReference().child("careNeeder").child(GlobalVariable.Uid).child("EmmergencyMessage");
        Emergency_msg task = new Emergency_msg(id, msg);
        rootRef.child(id).setValue(task);
        Toast.makeText(context, "Draft updated", Toast.LENGTH_SHORT).show();

    }
}
