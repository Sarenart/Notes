package com.example.notes.models;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TODORepository {
    private TODODao todoDao;

    private LiveData<List<TODO>> todos;

    public TODORepository(Application application){
        TODODatabase todoDb = TODODatabase.getInstance(application);
        todoDao = todoDb.getToDoDao();
    }

    public LiveData<List<TODO>> getAll(){
        return todoDao.getAll();
    }

    public void insertTODO(TODO todo){
        new InsertTODOAsyncTask(todoDao).execute(todo);
    }

    private static class InsertTODOAsyncTask extends AsyncTask<TODO, Void, Void>{

        private TODODao todoDao;

        public InsertTODOAsyncTask(TODODao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(TODO... todos) {

            todoDao.insert(todos[0]);
            return null;
        }
    }

    public void updateTODO(TODO todo){
        new UpdateTODOAsyncTask(todoDao).execute(todo);
    }

    private static class UpdateTODOAsyncTask extends AsyncTask<TODO, Void, Void>{

        private TODODao todoDao;

        public UpdateTODOAsyncTask(TODODao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(TODO... todos) {

            todoDao.update(todos[0]);
            return null;
        }
    }

    public void deleteTODO(TODO todo){
        new DeleteTODOAsyncTask(todoDao).execute(todo);
    }

    private static class DeleteTODOAsyncTask extends AsyncTask<TODO, Void, Void>{

        private TODODao todoDao;

        public DeleteTODOAsyncTask(TODODao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(TODO... todos) {

            todoDao.delete(todos[0]);
            return null;
        }
    }
}

