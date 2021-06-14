package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.notes.databinding.ActivityShowTodoactivityBinding;
import com.example.notes.models.TODO;

public class ShowTODOActivity extends AppCompatActivity {


    private TODO selectedTodo;
    private ActivityShowTodoactivityBinding activityShowTodoactivityBinding;
    private ShowTODOClickHandlers showTODOClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_todoactivity);

        activityShowTodoactivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_todoactivity);

        Intent intent = getIntent();

        selectedTodo = new TODO();
        selectedTodo.setId(intent.getIntExtra(AddEditActivity.TODO_ID, -1));
        selectedTodo.setTitle(intent.getStringExtra(AddEditActivity.TODO_TITLE));
        selectedTodo.setDescription(intent.getStringExtra(AddEditActivity.TODO_DESCRIPTION));
        //int id = intent.getIntExtra(AddEditActivity.TODO_ID, 0);

        showTODOClickHandlers = new ShowTODOClickHandlers(this);

        activityShowTodoactivityBinding.setTodo(selectedTodo);
        activityShowTodoactivityBinding.setShowTODOClickHandler(showTODOClickHandlers);

    }
    public class ShowTODOClickHandlers {
        Context context;

        public ShowTODOClickHandlers(Context context) {
            this.context = context;
        }

        public void onEditButtonClicked(View view){

            Intent intent = new Intent();

            intent.putExtra(AddEditActivity.TODO_TITLE, selectedTodo.getTitle());
            intent.putExtra(AddEditActivity.TODO_DESCRIPTION, selectedTodo.getDescription());
            intent.putExtra(AddEditActivity.TODO_ID, selectedTodo.getId());

            setResult(MainActivity.RESULT_UPDATE, intent);
            finish();


        }
    }

}