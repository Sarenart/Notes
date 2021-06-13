package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.notes.databinding.ActivityShowTodoactivityBinding;
import com.example.notes.models.TODO;

public class ShowTODOActivity extends AppCompatActivity {


    private TODO selectedTodo;
    private ActivityShowTodoactivityBinding activityShowTodoactivityBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_todoactivity);

        activityShowTodoactivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_todoactivity);

        Intent intent = getIntent();

        selectedTodo = new TODO();
        selectedTodo.setTitle(intent.getStringExtra(AddEditActivity.TODO_TITLE));
        selectedTodo.setDescription(intent.getStringExtra(AddEditActivity.TODO_DESCRIPTION));
        //int id = intent.getIntExtra(AddEditActivity.TODO_ID, 0);
        activityShowTodoactivityBinding.setTodo(selectedTodo);

    }
}