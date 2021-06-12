package com.example.notes;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notes.Models.TODO;

public class MainActivityViewModel extends ViewModel {

    private TODO[] todos;
    private MutableLiveData<TODO[]> todosLiveData;


}
