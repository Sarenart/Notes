package com.example.notes.Models;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TODO.class}, version = 1)
public abstract class TODODatabase extends RoomDatabase {

    private static TODODatabase instance;

    public abstract ToDoDao getToDoDao();

    public static synchronized TODODatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TODODatabase.class, "TODODb")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private ToDoDao todoDao;

        public InitialDataAsyncTask(TODODatabase db){
            todoDao = db.getToDoDao();
        }

        @Override
        protected  Void doInBackground(Void...voids){
            return null;
        }
    }
}
