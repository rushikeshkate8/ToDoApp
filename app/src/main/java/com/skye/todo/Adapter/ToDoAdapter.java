package com.skye.todo.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.skye.todo.MainActivity;
import com.skye.todo.Model.ToDoModel;
import com.skye.todo.R;
import com.skye.todo.Utils.AddNewTask;
import com.skye.todo.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> toDoModelList;
    private MainActivity mainActivity;
    private DataBaseHelper myDB;
    public ToDoAdapter(DataBaseHelper myDB, MainActivity mainActivity)
    {
        this.myDB = myDB;
        this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        ViewGroup root;
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.task_item, parent, false );
        return new MyViewHolder( v );
    }
    public boolean toBoolean(int num)
    {
        return num != 0;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder , int position) {
        final ToDoModel item = toDoModelList.get( position );
        holder.checkBox.setChecked( toBoolean( item.getStatus() ) );
        holder.checkBox.setText( item.getTask() );
        if(holder.checkBox.isChecked())
        {
            holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
        }
        holder.checkBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView , boolean isChecked) {
                if(isChecked)
                {
                    holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
                    myDB.updateStatus( item.getId(), 1 );
                }
                else
                {
                    if((holder.checkBox.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0)
                        holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG) );
                    myDB.updateStatus( item.getId(), 0 );
                }
            }
        } );
    }
    public Context getContext()
    {
        return mainActivity;
    }
    public void setTasks(List<ToDoModel> mList)
    {
        toDoModelList = mList;
        notifyDataSetChanged();
    }
    public void deleteTask(int position)
    {
        ToDoModel item = toDoModelList.get( position );
        myDB.deleteTask( item.getId() );
        toDoModelList.remove( position );
        notifyItemRemoved( position );
    }
    public void editItem(int position)
    {
        ToDoModel item = toDoModelList.get( position );
        Bundle bundle = new Bundle();
        bundle.putString( "task", item.getTask() );
        bundle.putInt( "id", item.getId() );
        AddNewTask task = new AddNewTask();
        task.setArguments( bundle );
        task.show( mainActivity.getSupportFragmentManager(), task.getTag() );
    }
    @Override
    public int getItemCount() {
        return toDoModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        MaterialCheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            checkBox = itemView.findViewById( R.id.checkbox );
        }
    }
}
