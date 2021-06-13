package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivityMainBinding;
import com.example.notes.models.TODO;
import com.example.notes.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers mainActivityClickHandlers;
    private ArrayList<TODO> todoArrayList;
    private RecyclerView recyclerView;
    private TODOAdapter todoAdapter;
    //private int selectedTodoId;

    public static final int ADD_TODO_CODE = 0;
    public static final int EDIT_TODO_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);

        mainActivityClickHandlers = new MainActivityClickHandlers(this);

        activityMainBinding.setMainActivityClickHandlers(mainActivityClickHandlers);

        mainActivityViewModel.getAll().observe(this, new Observer<List<TODO>>() {
            @Override
            public void onChanged(List<TODO> todos) {
                todoArrayList = (ArrayList<TODO>) todos;
                loadRecyclerView();
            }
        });


    }


    private void loadTodosInArrayList(){
        mainActivityViewModel.getAll().observe(this, new Observer<List<TODO>>() {
            @Override
            public void onChanged(List<TODO> todos) {
               todoArrayList = (ArrayList<TODO>) todos;
            }
        });
    }

    private void loadRecyclerView(){
        recyclerView = activityMainBinding.todoRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        todoAdapter = new TODOAdapter();
        todoAdapter.setTodoArrayList(todoArrayList);
        recyclerView.setAdapter(todoAdapter);
        todoAdapter.setOnItemClickListener(new TODOAdapter.onItemClickListener() {
            @Override
            public void onItemClick(TODO todo) {
                Intent intent = new Intent(MainActivity.this, ShowTODOActivity.class);

                intent.putExtra(AddEditActivity.TODO_TITLE, todo.getTitle());
                intent.putExtra(AddEditActivity.TODO_DESCRIPTION, todo.getDescription());
                intent.putExtra(AddEditActivity.TODO_ID, todo.getId());
                startActivity(intent);
            }
        });
    }

    public class MainActivityClickHandlers {
        Context context;

        public MainActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onAddGoalClicked(View view){
            Log.d("WORKWORK", "ItsWorking");
           // Toast.makeText(context, "Add goal is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_TODO_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TODO_CODE && resultCode == RESULT_OK){
            TODO todo = new TODO();
            todo.setDescription(data.getStringExtra(AddEditActivity.TODO_DESCRIPTION));
            todo.setTitle(data.getStringExtra(AddEditActivity.TODO_TITLE));

            mainActivityViewModel.add(todo);
        }

        else if(requestCode == ADD_TODO_CODE && resultCode == RESULT_OK){

        }
    }
}