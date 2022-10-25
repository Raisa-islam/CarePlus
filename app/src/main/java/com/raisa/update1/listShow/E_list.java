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
import com.raisa.update1.object.Emergency_msg;

import java.util.List;

public class E_list extends ArrayAdapter<Emergency_msg> {
    private Activity context;
    private List<Emergency_msg> msgList;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.messge_item, null, true);

        TextView title = listViewItem.findViewById(R.id.title);


        Emergency_msg task = msgList.get(position);
        title.setText(task.getMsg());
        return listViewItem;
    }

    public E_list(Activity context, List<Emergency_msg>msgList)
    {
        super(context, R.layout.messge_item, msgList);
        this.context = context;
        this.msgList = msgList;
    }
}
