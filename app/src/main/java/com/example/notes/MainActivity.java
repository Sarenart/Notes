package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.notes.databinding.ActivityMainBinding;
import com.example.notes.models.TODO;
import com.example.notes.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private ArrayList<TODO> todoArrayList;
    private RecyclerView recyclerView;
    private TODOAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);

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
    }
}