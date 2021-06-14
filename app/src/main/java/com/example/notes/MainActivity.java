package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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

    public static final int RESULT_UPDATE = 333;

    public static final int ADD_TODO_CODE = 0;
    public static final int EDIT_TODO_CODE = 1;
    public static final int REVIEW_TODO = 2;

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

                startActivityForResult(intent, REVIEW_TODO);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                TODO todo = todoArrayList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.delete(todo);
            }
        }).attachToRecyclerView(recyclerView);
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
        switch (requestCode){
        case ADD_TODO_CODE:
            if (resultCode == RESULT_OK) {
                TODO todo = new TODO();
                todo.setDescription(data.getStringExtra(AddEditActivity.TODO_DESCRIPTION));
                todo.setTitle(data.getStringExtra(AddEditActivity.TODO_TITLE));

                mainActivityViewModel.add(todo);
            }
        case EDIT_TODO_CODE:
            if (resultCode == RESULT_OK) {

                TODO todo = new TODO();

                todo.setId(data.getIntExtra(AddEditActivity.TODO_ID, -1));
                todo.setTitle(data.getStringExtra(AddEditActivity.TODO_TITLE));
                todo.setDescription(data.getStringExtra(AddEditActivity.TODO_DESCRIPTION));

                mainActivityViewModel.update(todo);
            }

        case REVIEW_TODO:
            if (resultCode == RESULT_UPDATE) {
                Intent intent;

                TODO todo = new TODO();

                todo.setId(data.getIntExtra(AddEditActivity.TODO_ID, -1));
                todo.setTitle(data.getStringExtra(AddEditActivity.TODO_TITLE));
                todo.setDescription(data.getStringExtra(AddEditActivity.TODO_DESCRIPTION));

                intent = new Intent(MainActivity.this, AddEditActivity.class);

                intent.putExtra(AddEditActivity.TODO_ID, todo.getId());
                intent.putExtra(AddEditActivity.TODO_TITLE, todo.getTitle());
                intent.putExtra(AddEditActivity.TODO_DESCRIPTION, todo.getDescription());

                startActivityForResult(intent, EDIT_TODO_CODE);

            }
    }
    }
}