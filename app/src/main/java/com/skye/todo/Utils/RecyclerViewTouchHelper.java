package com.skye.todo.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.skye.todo.Adapter.ToDoAdapter;
import com.skye.todo.MainActivity;
import com.skye.todo.Model.ToDoModel;
import com.skye.todo.R;

import java.util.Collection;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback {
   private ToDoAdapter adapter;
    public RecyclerViewTouchHelper(ToDoAdapter adapter) {
        super( ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView , @NonNull RecyclerView.ViewHolder viewHolder , @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
          /*
            ToDoModel toTaskItem = adapter.toDoModelList.get( toPosition );
            int toID = toTaskItem.getId();
            String toTask = toTaskItem.getTask();
            int toStatus = toTaskItem.getStatus();

            ToDoModel fromTaskItem = adapter.toDoModelList.get( fromPosition );
            int fromID = fromTaskItem.getId();
            String fromTask = fromTaskItem.getTask();
            int fromStatus = fromTaskItem.getStatus();

            adapter.myDB.updateTask( toID, fromTask );
            adapter.myDB.updateStatus( toID, fromStatus );

            adapter.myDB.updateTask( fromID, toTask );
            adapter.myDB.updateStatus( fromID, toStatus );
     */

            Collections.swap( adapter.toDoModelList, fromPosition, toPosition );
            adapter.notifyItemMoved( fromPosition, toPosition );
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder , int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.RIGHT)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder( adapter.getContext() );
            builder.setTitle( "Delete Task" );
            builder.setMessage( "Are You Sure ?" );
            builder.setPositiveButton( "Yes" , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog , int which) {
                    adapter.deleteTask( position );
                }
            } );
            builder.setNegativeButton( "No" , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog , int which) {
                }
            } );

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            adapter.notifyItemChanged(position);
        }
        else{
            adapter.editItem( position );
            adapter.notifyItemChanged(position);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c , @NonNull RecyclerView recyclerView , @NonNull RecyclerView.ViewHolder viewHolder , float dX , float dY , int actionState , boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor( ContextCompat.getColor( adapter.getContext(), R.color.light_blue ) )
                .addSwipeLeftActionIcon( R.drawable.ic_baseline_edit )
                .addSwipeRightBackgroundColor( ContextCompat.getColor( adapter.getContext(), R.color.light_red ) )
                .addSwipeRightActionIcon( R.drawable.ic_baseline_delete )
                .create()
                .decorate();
        super.onChildDraw( c , recyclerView , viewHolder , dX , dY , actionState , isCurrentlyActive );
    }
}
