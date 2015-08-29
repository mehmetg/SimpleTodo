/*
 * Copyright Mehmet Gerceker (c) 2015.
 */

package com.mehmetg.simpletodo.model;

import android.graphics.Color;

/**
 * Created by mehmetgerceker on 8/28/15.
 */
public class TodoListItem {

    private String itemText;
    private boolean completed;

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
    /*
    public TodoListItem() {
        this.itemText = "";
        this.completed = false;
    }
    */

    public TodoListItem(String text) {
        this.itemText = text;
        this.completed = false;
    }


}
