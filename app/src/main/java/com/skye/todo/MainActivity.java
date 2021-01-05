package com.skye.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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
   boolean darkMode = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.action_bar_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.aboutus_menu_item:
                startActivity(new Intent(this, AboutUsActivity.class) );
                return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataBaseHelper.db.delete( DataBaseHelper.TABLE_NAME, null, null );
        for(ToDoModel task : adapter.toDoModelList)
        {
            dataBaseHelper.insertTask( task );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        int nightFlags = this.getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        if(nightFlags == Configuration.UI_MODE_NIGHT_YES)
            darkMode = true;

        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setLogo( R.drawable.app_icon_for_main_activity );
        getSupportActionBar().setDisplayUseLogoEnabled( true );
        String appName = " " + getSupportActionBar().getTitle().toString();
        if(darkMode)
            getSupportActionBar().setTitle( Html.fromHtml(  "<font color= '#FFFFFF'>" + "&nbsp;" + appName + "</font>" ));
        else
            getSupportActionBar().setTitle( Html.fromHtml(  "<font color= '#757575'>" + "&nbsp;" + appName + "</font>" ));

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
        //Collections.reverse(toDoModelList);
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
        //Collections.reverse(toDoModelList);
        adapter.setTasks( toDoModelList );
        adapter.notifyDataSetChanged();
    }
}