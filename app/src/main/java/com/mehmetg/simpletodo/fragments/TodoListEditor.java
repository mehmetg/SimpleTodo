package com.mehmetg.simpletodo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mehmetg.simpletodo.R;
import com.mehmetg.simpletodo.model.TodoListItem;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that DialogFragment this fragment must implement the
 * {@link TodoListEditor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodoListEditor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoListEditor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "item";
    private static final String ARG_PARAM2 = "pos";

    // TODO: Rename and change types of parameters
    private TodoListItem item;

    private int position;
    EditText etTaskName;


    TextView etTaskDue;
    TextView tvTaskCreated;
    CheckBox cbCompleted;

    public TextView getEtTaskDue() {
        return etTaskDue;
    }

    public int getPosition() {
        return position;
    }

    public TodoListItem getItem() {
        item.setCompleted(cbCompleted.isChecked());
        item.setItemText(etTaskName.getText().toString());
        item.setDateDue(etTaskDue.getText().toString());
        return item;
    }
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param item to_do list item
     * @param pos item position
     * @return A new instance of fragment TodoListEditor.
     */
    // TODO: Rename and change types and number of parameters
    public static TodoListEditor newInstance(String item, int pos) {
        TodoListEditor fragment = new TodoListEditor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, item);
        args.putInt(ARG_PARAM2, pos);
        fragment.setArguments(args);
        return fragment;
    }

    public TodoListEditor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = TodoListItem.fromString(getArguments().getString(ARG_PARAM1));
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_todo_list_editor, container, false);
        etTaskName = (EditText) v.findViewById(R.id.task_desc);
        etTaskDue = (TextView) v.findViewById(R.id.date_due);
        tvTaskCreated = (TextView) v.findViewById(R.id.date_created);
        cbCompleted = (CheckBox) v.findViewById(R.id.completed);

        etTaskName.setText(item.getItemText());
        String dueDate = item.getDateDue();
        if (!dueDate.equals(TodoListItem.NONE)) {
            etTaskDue.setText(dueDate);
        }
        tvTaskCreated.setText(item.getDateCreated());
        cbCompleted.setChecked(item.isCompleted());
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(TodoListItem item);
    }



}
