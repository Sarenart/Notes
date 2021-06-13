package com.example.notes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notes.models.TODO;
import com.example.notes.models.TODORepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    TODORepository todoRepository;
    private LiveData<List<TODO>> todos;

    public MainActivityViewModel(@NonNull  Application application) {
        super(application);

        todoRepository = new TODORepository(application);
    }

    public LiveData<List<TODO>> getAll(){
        todos = todoRepository.getAll();
        return todos;
    }

    public void add(TODO todo){
        todoRepository.insertTODO(todo);
    }

    public void update(TODO todo){
        todoRepository.updateTODO(todo);
    }

    public void delete(TODO todo){
        todoRepository.deleteTODO(todo);
    }
}
