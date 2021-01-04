package com.skye.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skye.todo.Adapter.ToDoAdapter;
import com.skye.todo.Model.ToDoModel;
import com.skye.todo.Utils.AddNewTask;
import com.skye.todo.Utils.DataBaseHelper;
import com.skye.todo.Utils.RecyclerViewTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
   RecyclerView recyclerView;
   FloatingActionButton floatingActionButton;
   DataBaseHelper dataBaseHelper;
   private List<ToDoModel> toDoModelList;
   private ToDoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        recyclerView = findViewById( R.id.recyclerView );
        dataBaseHelper = new DataBaseHelper( MainActivity.this );
        floatingActionButton = findViewById( R.id.fab );

        floatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show( getSupportFragmentManager(), AddNewTask.TAG );
            }
        } );
        toDoModelList = new ArrayList<>();
        adapter = new ToDoAdapter( dataBaseHelper, MainActivity.this );

        toDoModelList = dataBaseHelper.getAllTasks();
        Collections.reverse(toDoModelList);
        adapter.setTasks( toDoModelList );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager(this) );
        recyclerView.setAdapter( adapter );
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper( new RecyclerViewTouchHelper( adapter ) );
        itemTouchHelper.attachToRecyclerView( recyclerView );
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        toDoModelList = dataBaseHelper.getAllTasks();
        Collections.reverse(toDoModelList);
        adapter.setTasks( toDoModelList );
        adapter.notifyDataSetChanged();
    }
}