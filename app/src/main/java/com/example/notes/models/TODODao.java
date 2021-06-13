package com.example.notes.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TODODao {

    @Insert
    void insert(TODO todo);

    @Update
    void update(TODO todo);

    @Delete
    void delete(TODO todo);

    @Query("select * from ToDo_table")
    LiveData<List<TODO>> getAll();

}
