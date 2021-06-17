package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivityAddEditBinding;
import com.example.notes.models.TODO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AddEditActivity extends AppCompatActivity {

    private TODO todo;

    public static final String TODO_ID = "todoId";
    public static final String TODO_TITLE = "todoTitle";
    public static final String TODO_DESCRIPTION = "todoDescription";
    public static final String TODO_URI = "todoUri";


    private AddEditActivityClickHandlers addEditClickHandlers;
    private ActivityAddEditBinding activityAddEditBinding;
    private final int Pick_image = 1;

    static final String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};

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
            todo.setUri(intent.getStringExtra(TODO_URI));

        }
        else{
            setTitle("Add TODO");
        }
        activityAddEditBinding.setTodo(todo);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, 69);
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
                intent.putExtra(TODO_URI, todo.getUri());

                setResult(RESULT_OK, intent);
                finish();

            }

        }
        public void onAddImageButtonClicked(View view){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, Pick_image);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case Pick_image:
                if(resultCode == RESULT_OK){
                    File storage = new File ( Environment.getExternalStorageDirectory(), "TODOImages");
                    boolean check = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

                    if (!storage.exists()) {

                        check = storage.mkdirs();
                    }
                    Log.d("DDDD", String.valueOf(check));
                    File imageFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "TODOImages" + File.separator, "Image_1.png");
                    if (imageFile.exists()) {
                        imageFile.delete();
                    }
                    try{
                        Uri imageUri = data.getData();

                        Bitmap imageToSave = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                       // FileOutputStream out = new FileOutputStream(imageFile);
                        //imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        //out.flush();
                        //out.close();


                        //imageUri = Uri.fromFile(imageFile);
                        //TODO Erase garbage
                        todo.setUri(imageUri.toString());
                    }
                    catch (FileNotFoundException ex){
                        ex.printStackTrace();
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                    catch (NullPointerException ex){
                        ex.printStackTrace();
                    }

                }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 69: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                } else {

                    // Disable the functionality that
                    // depends on this permission.
                    Toast.makeText(AddEditActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}