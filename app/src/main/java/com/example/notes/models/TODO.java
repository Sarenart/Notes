package com.example.notes.models;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.notes.AddEditActivity;
import com.squareup.picasso.Picasso;

@Entity(tableName = "TODO_table")
public class TODO extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="TODO_Title")
    private String title;

    @ColumnInfo(name="TODO_Description")
    private String description;

    @ColumnInfo(name = "TODO_Uri")
    private String uri;

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
    public String getUri() { return uri; }

    @Bindable
    public int getId() {
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
        notifyPropertyChanged(BR.title);
    }


    public void setDescription(String description){
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public void setUri(String uri) {
        this.uri = uri;
        notifyPropertyChanged(BR.uri);
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @BindingAdapter({"app:url", "app:error"})
    public static void loadImage(ImageView view, String url, Drawable errorImage){
        Picasso.get().load(url).error(errorImage).into(view);

        if(url == null)
            view.setVisibility(View.GONE);
    }

    @BindingAdapter({"app:url", "app:error", "app:placeholder"})
    public static void loadImage(ImageView view, String url, Drawable errorImage, Drawable placeholder){

        Picasso.get().load(url).placeholder(placeholder).error(errorImage).into(view);

    }
}
