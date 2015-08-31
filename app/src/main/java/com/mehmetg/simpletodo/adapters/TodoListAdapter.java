/*
 * Copyright Mehmet Gerceker (c) 2015.
 */

package com.mehmetg.simpletodo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehmetg.simpletodo.R;
import com.mehmetg.simpletodo.model.TodoListItem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mehmetgerceker on 8/29/15.
 */
public class TodoListAdapter extends ArrayAdapter<TodoListItem>{

    public TodoListAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }

    public TodoListAdapter(Context context, int resource,  List<TodoListItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.view_todo_list_row, null);
        }

        TodoListItem p = getItem(position);

        if (p != null) {
            TextView tvDesc = (TextView) v.findViewById(R.id.description);
            TextView tvDueDate = (TextView) v.findViewById(R.id.task_due_date);
            TextView tvCreatedDate = (TextView) v.findViewById(R.id.task_created_date);
            if (tvDesc != null) {

                tvCreatedDate.setText(p.getDateCreated());
                tvDueDate.setText(p.getDateDue());

                tvDesc.setText(p.getItemText());
                this.setCompleted(v, p.isCompleted());
            }
        }
        return v;
    }

    private void setCompleted(View v, boolean completed) {
        TextView taskDescription = (TextView) v.findViewById(R.id.description);
        TextView tvDueDate = (TextView) v.findViewById(R.id.task_due_date);
        if (completed) {
            taskDescription.setTypeface(null, Typeface.NORMAL);
            taskDescription.setTextColor(Color.GRAY);
            tvDueDate.setTextColor(Color.BLACK);
        } else {
            //get today's date
            String dueDateString = tvDueDate.getText().toString();
            if (!dueDateString.equals(TodoListItem.NONE) && dueDateString.length() > 0) {
                try {
                    Date dueDate = TodoListItem.SIMPLE_DATE_FORMAT.parse(dueDateString);
                    Date now = Calendar.getInstance().getTime();
                    if (dueDate.before(now)) {
                        tvDueDate.setTextColor(Color.RED);
                    } else {
                        tvDueDate.setTextColor(Color.BLACK);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //if past due mark date red.
            taskDescription.setTypeface(null, Typeface.BOLD);
            taskDescription.setTextColor(Color.BLACK);
        }
    }
}
