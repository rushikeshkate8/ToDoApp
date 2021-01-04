package com.skye.todo.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.skye.todo.Adapter.ToDoAdapter;
import com.skye.todo.MainActivity;
import com.skye.todo.R;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback {
   private ToDoAdapter adapter;
    public RecyclerViewTouchHelper(ToDoAdapter adapter) {
        super( 0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView , @NonNull RecyclerView.ViewHolder viewHolder , @NonNull RecyclerView.ViewHolder target) {
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
                .addSwipeLeftBackgroundColor( ContextCompat.getColor( adapter.getContext(), R.color.blue ) )
                .addSwipeLeftActionIcon( R.drawable.ic_baseline_edit )
                .addSwipeRightBackgroundColor( Color.RED )
                .addSwipeRightActionIcon( R.drawable.ic_baseline_delete )
                .create()
                .decorate();
        super.onChildDraw( c , recyclerView , viewHolder , dX , dY , actionState , isCurrentlyActive );
    }
}
