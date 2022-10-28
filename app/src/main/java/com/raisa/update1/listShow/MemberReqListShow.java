package com.raisa.update1.listShow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.raisa.update1.R;
import com.raisa.update1.object.Member;
import com.raisa.update1.object.Model;

import java.util.List;

public class MemberReqListShow extends ArrayAdapter<Model> {
    private Activity context;
    private List<Model> taskList;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.member_list, null, true);

        TextView Name = listViewItem.findViewById(R.id.memberName);
        TextView Email = listViewItem.findViewById(R.id.memberEmail);
        TextView sendRequest = listViewItem.findViewById(R.id.sendRequest);




        Model task = taskList.get(position);
        Name.setText(task.getEmail());
        Email.setText(task.getName());



        return listViewItem;
    }
    public MemberReqListShow(Activity context, List<Model>taskList)
    {
        super(context, R.layout.member_list, taskList);
        this.context = context;
        this.taskList = taskList;
    }
}
