package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivityAddEditBinding;
import com.example.notes.models.TODO;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddEditActivity extends AppCompatActivity {

    private TODO todo;

    public static final String TODO_ID = "todoId";
    public static final String TODO_TITLE = "todoTitle";
    public static final String TODO_DESCRIPTION = "todoDescription";
    private AddEditActivityClickHandlers addEditClickHandlers;
    private ActivityAddEditBinding activityAddEditBinding;
    private final int Pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        todo = new TODO();

        activityAddEditBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_add_edit);


        addEditClickHandlers = new AddEditActivityClickHandlers(this);

        activityAddEditBinding.setAddEditActivityClickHandler(addEditClickHandlers);


        Intent intent = getIntent();

        if(intent.hasExtra(TODO_ID)){

            setTitle("Edit TODO");

            todo.setId(intent.getIntExtra(TODO_ID, 0));
            todo.setTitle(intent.getStringExtra(TODO_TITLE));
            todo.setDescription(intent.getStringExtra(TODO_DESCRIPTION));

        }
        else{
            setTitle("Add TODO");

        }
        activityAddEditBinding.setTodo(todo);
    }

    public class AddEditActivityClickHandlers{
        Context context;

        public AddEditActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onOkButtonClicked(View view){

            if(todo.getTitle()==null){
                Toast.makeText(context, "Enter the title", Toast.LENGTH_LONG).show();
            }
            else{

                Intent intent = new Intent();
                intent.putExtra(TODO_ID, todo.getId());
                intent.putExtra(TODO_TITLE, todo.getTitle());
                intent.putExtra(TODO_DESCRIPTION, todo.getDescription());
                setResult(RESULT_OK, intent);
                finish();

            }

        }
        /*public void onAddImageButtonClicked(View view){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, Pick_image);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                    }catch(FileNotFoundException ex){
                        ex.printStackTrace();
                    }
                }
        }

    }
}