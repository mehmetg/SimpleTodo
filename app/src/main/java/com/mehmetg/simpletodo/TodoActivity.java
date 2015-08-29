/*
 * Copyright Mehmet Gerceker (c) 2015.
 */

package com.mehmetg.simpletodo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.mehmetg.simpletodo.adapters.TodoListAdapter;
import com.mehmetg.simpletodo.model.TodoListItem;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    ArrayList<TodoListItem> items;
    TodoListAdapter itemsAdapter;
    ListView lvItems;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();

        this.readTodoItemsFile();
        itemsAdapter = new TodoListAdapter(this, R.layout.todo_list_row, items);
        lvItems.setAdapter(itemsAdapter);
        registerForContextMenu(lvItems);
        this.setupListViewListener();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        this.hideSoftKeyboard();
        if (v.getId() == R.id.lvItems) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(items.get(info.position).getItemText());
            String[] menuItems = getResources().getStringArray(R.array.menu);
            ArrayList<String> menuList = new ArrayList<>(Arrays.asList(menuItems));
            if (items.get(info.position).isCompleted()) {
                menuList.remove(getString(R.string.Done));
            }
            for (int i = 0; i < menuList.size(); i++) {
                menu.add(Menu.NONE, i, i, menuList.get(i));
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String menuItemName = item.getTitle().toString();
        // String listItemName = items.get(info.position).getItemText();
        if (menuItemName.equals(getString(R.string.Edit))) {
            //this.items.remove(info.position);
            //this.itemsAdapter.notifyDataSetChanged();
            //this.writeTodoItemsFile();
        } else if (menuItemName.equals(getString(R.string.Delete))) {
            this.items.remove(info.position);
        } else if (menuItemName.equals(getString(R.string.Done))) {
            this.items.get(info.position).setCompleted(true);
        }
        this.itemsAdapter.notifyDataSetChanged();
        this.writeTodoItemsFile();
        return true;
    }

    public void setupListViewListener() {
        /*
        this.lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeTodoItemsFile();
                        return true;
                    }
                });
        */
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
        editText = (EditText) findViewById(R.id.etNewItem);
        TodoListItem newItem = new TodoListItem(this.editText.getText().toString().trim());
        itemsAdapter.add(newItem);
        editText.setText("");
        this.writeTodoItemsFile();
        this.hideSoftKeyboard();
    }

    private boolean readTodoItemsFile() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            List<String> items = FileUtils.readLines(todoFile);

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
            FileUtils.writeLines(todoFile, this.items);
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

}
