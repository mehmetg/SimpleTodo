/*
 * Copyright Mehmet Gerceker (c) 2015.
 */

package com.mehmetg.simpletodo.model;

import android.graphics.Color;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mehmetgerceker on 8/28/15.
 */
public class TodoListItem {

    private String itemText;
    private boolean completed;
    private Date dateCreated;
    private Date dateDue;
    private int color;


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

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    private void setDateCreated(Date dateCreated) {
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
        this.dateCreated = Calendar.getInstance().getTime();
        this.dateDue = null;
        this.color = Color.TRANSPARENT;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.itemText)
                .append(",")
                .append(Long.toString(this.dateCreated.getTime()))
                .append(",");

        if (this.dateDue != null){
            sb.append(Long.toString(this.dateDue.getTime()))
                    .append(",");
        } else {
            sb.append("Null")
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
            Calendar cal = Calendar.getInstance();
            item = new TodoListItem(parts[0]);

            cal.setTimeInMillis(Long.parseLong(parts[1]));
            item.setDateCreated(cal.getTime());
            if (parts[2].equals("Null")) {
                item.setDateDue(null);
            } else {
                cal.setTimeInMillis(Long.parseLong(parts[2]));
                item.setDateDue(cal.getTime());
            }
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
