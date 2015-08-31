/*
 * Copyright Mehmet Gerceker (c) 2015.
 */

package com.mehmetg.simpletodo.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mehmetg.simpletodo.R;
import com.mehmetg.simpletodo.fragments.TodoListEditor;
import com.mehmetg.simpletodo.adapters.TodoListAdapter;
import com.mehmetg.simpletodo.model.TodoListItem;
import com.mehmetg.simpletodo.fragments.TodoListEditor.OnFragmentInteractionListener;

import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    ArrayList<TodoListItem> items;
    TodoListAdapter itemsAdapter;
    ListView lvItems;
    EditText editText;
    Button addButton;
    TodoListEditor todoListEditor;
    Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        editText = (EditText) findViewById(R.id.etNewItem);
        addButton = (Button) findViewById(R.id.btnAddItem);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        items = new ArrayList<>();
        this.readTodoItemsFile();
        itemsAdapter = new TodoListAdapter(this, R.layout.view_todo_list_row, items);
        lvItems.setAdapter(itemsAdapter);
        this.setupListViewListener();
        this.configureInputs();


    }

    public void setupListViewListener() {

        this.lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        TodoListItem todoItem = items.get(pos);
                        //Launch fragment.
                        FragmentManager fm = getFragmentManager();
                        todoListEditor = TodoListEditor.newInstance(todoItem.toString(), pos);
                        fm.beginTransaction()
                                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                                .add(R.id.editor_holder, todoListEditor)
                                .commit();
                        ;
                        itemsAdapter.notifyDataSetChanged();
                        writeTodoItemsFile();
                        return true;
                    }
                });

        this.lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hideSoftKeyboard();
                        items.get(position).toggleCompleted();
                        itemsAdapter.notifyDataSetChanged();
                        writeTodoItemsFile();
                    }

                }
        );
    }

    public void onAddItem(View v) {
        TodoListItem newItem = new TodoListItem(this.editText.getText().toString().trim());
        itemsAdapter.add(newItem);
        editText.setText("");
        this.writeTodoItemsFile();
        this.hideSoftKeyboard();
        v.startAnimation(shake);
    }

    private boolean readTodoItemsFile() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            List<String> lines = FileUtils.readLines(todoFile);
            for (String line:lines){
                TodoListItem tdli = TodoListItem.fromString(line);
                if (tdli != null) {
                    this.items.add(tdli);
                }
            }
            return true;
        } catch (IOException e) {
            this.items = new ArrayList<>();
            e.printStackTrace();
            return false;
        }
    }

    private boolean writeTodoItemsFile() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            if (this.items == null || this.items.size() < 1) {
                todoFile.delete();
            } else {
                boolean append = false;
                for (TodoListItem item : this.items) {
                    String s = item.toString();
                    FileUtils.writeStringToFile(todoFile, s + "\n", append);
                    append = true;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void onFragmentInteraction(TodoListItem item){
        System.out.print(String.format("Interact! %s", item.toString()));
    }

    //put this into an interface of the editor fragment
    public void onCloseClick(View v){
        removeEditor();
    }
    public void onDeleteClick(View v){
        int p = todoListEditor.getPosition();
        this.items.remove(p);
        itemsAdapter.notifyDataSetChanged();
        removeEditor();
    }
    public void onSaveClick(View v){
        int p = todoListEditor.getPosition();
        this.items.set(p, todoListEditor.getItem());
        itemsAdapter.notifyDataSetChanged();
        removeEditor();
    }
    private void removeEditor(){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .remove(todoListEditor)
                .commit();
    }
    private void configureInputs() {
        addButton.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean enabled = editText.getText().toString().trim().length() > 0;
                addButton.setEnabled(enabled);
                if (enabled) {
                    addButton.startAnimation(shake);
                }

            }
        });
    }

}
