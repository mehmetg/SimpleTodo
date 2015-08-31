/*
 * Copyright Mehmet Gerceker (c) 2015.
 */

package com.mehmetg.simpletodo.model;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mehmetgerceker on 8/28/15.
 */
public class TodoListItem {

    public final static String NONE = "None";
    private String itemText;
    private boolean completed;
    private String dateCreated;
    private String dateDue;
    private int color;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void toggleCompleted(){
        this.completed = !this.completed;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    private void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public TodoListItem(String text) {
        this.itemText = text;
        this.completed = false;
        this.dateCreated = SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime());
        this.dateDue = NONE;
        this.color = Color.TRANSPARENT;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.itemText)
                .append(",")
                .append(this.dateCreated)
                .append(",");

        if (this.dateDue != null){
            sb.append(this.dateDue)
                    .append(",");
        } else {
            sb.append(NONE)
                    .append(",");
        }
        sb.append(Integer.toString(this.color))
                .append(",");
        sb.append(String.valueOf(this.completed));

        return sb.toString();
    }

    public static TodoListItem fromString(String csvLine) {
        String[] parts = csvLine.split(",");
        TodoListItem item = null;
        try {
            item = new TodoListItem(parts[0]);
            item.setDateCreated(parts[1]);
            item.setDateDue(parts[2]);
            item.setColor(Integer.parseInt(parts[3]));
            item.setCompleted(Boolean.parseBoolean(parts[4]));
        } catch (Exception e) {
            item = null;
            e.printStackTrace();
        } finally {
            return item;
        }
    }
}
