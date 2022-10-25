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
import com.raisa.update1.object.Event;
import com.raisa.update1.object.Task;

import java.util.List;

public class EventListShow extends ArrayAdapter<Event> {

    private Activity context;
    private List<Event> taskList;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.rv_event_card, null, true);

        TextView title = listViewItem.findViewById(R.id.title);
        TextView description = listViewItem.findViewById(R.id.description);
        TextView hour = listViewItem.findViewById(R.id.hour);
        TextView min = listViewItem.findViewById(R.id.min);
        TextView days = listViewItem.findViewById(R.id.day);
        //TextView status = listViewItem.findViewById(R.id.status);


        Event task = taskList.get(position);
        title.setText(task.getTitle());
        description.setText(task.getDescription());
        hour.setText(task.getHour());
        min.setText(task.getMin());
        //days.setText(task.get_day());
        //status.setText(task.get);


        return listViewItem;
    }
    public EventListShow(Activity context, List<Event>taskList)
    {
        super(context, R.layout.rv_event_card, taskList);
        this.context = context;
        this.taskList = taskList;
    }
}
