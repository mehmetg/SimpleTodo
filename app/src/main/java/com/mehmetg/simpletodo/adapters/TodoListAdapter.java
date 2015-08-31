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
            TextView t = (TextView) v.findViewById(R.id.description);
            if (t != null) {
                t.setText(p.getItemText());
                this.setCompleted(v, p.isCompleted());
            }
        }
        return v;
    }

    private void setCompleted(View v, boolean completed) {
        ImageView statusIndicator = (ImageView) v.findViewById(R.id.status_indicator_image);
        TextView taskDescription = (TextView) v.findViewById(R.id.description);
        if (completed) {
            statusIndicator.setImageResource(R.drawable.ic_v);
            taskDescription.setTypeface(null, Typeface.NORMAL);
            //taskDescription.setTextColor(v.getResources().getColor(R.color.grey, null));
        } else {
            statusIndicator.setImageResource(R.drawable.ic_x);
            taskDescription.setTypeface(null, Typeface.BOLD);
            //taskDescription.setTextColor(v.getResources().getColor(R.color.black, null));
        }
    }
}
