package com.example.notes.Models;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ToDo_table")
public class TODO extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="ToDo_Title")
    private String title;

    @ColumnInfo(name="ToDo_Description")
    private String description;

    public TODO(){

    }

    public TODO(String Description, String Title){
        title = Title;
        description = Description;
    }

    @Bindable
    public String getTitle(){
        return title;
    }

    @Bindable
    public String getDescription(){
        return description;
    }

    @Bindable
    public int getId() {
        return this.id;
    }

    public void setTitle(String Title){
        title = Title;
        notifyPropertyChanged(BR.title);
    }


    public void setDescription(String Description){
        description = Description;
        notifyPropertyChanged(BR.description);
    }


    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
}
