package com.example.notes.models;

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

    public abstract TODODao getToDoDao();

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
        private TODODao todoDao;

        public InitialDataAsyncTask(TODODatabase db){
            todoDao = db.getToDoDao();
        }

        @Override
        protected  Void doInBackground(Void...voids){
            TODO checkMemes = new TODO();
            checkMemes.setId(1);
            checkMemes.setTitle("Посмотреть мемы со Шлёппой");
            checkMemes.setDescription("Шлёппа смешной. Каракалы смешные. Хочу каралака.");

            TODO progressWithStudy = new TODO();
            progressWithStudy.setId(1);
            progressWithStudy.setTitle("Посмотреть архитектуру Android");
            progressWithStudy.setDescription("Архитектурный подход очень сильно напоминает работу с Entity Framework и 3-хслойной архитектурой на третьем курсе. Хотя WPF было немного проще в освоении, MVVM в Android тоже не слишком сложный.");

            todoDao.insert(checkMemes);
            todoDao.insert(progressWithStudy);

            return null;
        }
    }
}
