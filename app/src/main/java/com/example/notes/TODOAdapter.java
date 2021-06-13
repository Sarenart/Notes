package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.databinding.TodoViewItemBinding;
import com.example.notes.models.TODO;

import java.util.ArrayList;

public class TODOAdapter extends RecyclerView.Adapter<TODOAdapter.TODOViewHolder>{

    private onItemClickListener onItemClickListener;
    private ArrayList<TODO> todoArrayList = new ArrayList<>();

    @NonNull
    @Override
    public TODOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TodoViewItemBinding todoViewItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.todo_view_item,
                parent, false
        );
        return new TODOViewHolder(todoViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  TODOAdapter.TODOViewHolder holder, int position) {

        TODO todo = todoArrayList.get(position);
        holder.todoViewItemBinding.setTodo(todo);

    }

    @Override
    public int getItemCount() {
        return todoArrayList.size();
    }

    class TODOViewHolder extends RecyclerView.ViewHolder{

        TodoViewItemBinding todoViewItemBinding;

        public TODOViewHolder(@NonNull TodoViewItemBinding todoViewItemBinding) {
            super(todoViewItemBinding.getRoot());
            this.todoViewItemBinding = todoViewItemBinding;
            todoViewItemBinding.getRoot().setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view){
                   int position = getAdapterPosition();
                   if(onItemClickListener != null && position!= RecyclerView.NO_POSITION)
                   {
                       onItemClickListener.onItemClick(todoArrayList.get(position));
                   }
               }
            });
        }
    }

    public interface onItemClickListener{

        void onItemClick(TODO todo) ;

    }

    public void setOnItemClickListener(TODOAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setTodoArrayList(ArrayList<TODO> todoArrayList) {
        this.todoArrayList = todoArrayList;
        notifyDataSetChanged();
    }
}
