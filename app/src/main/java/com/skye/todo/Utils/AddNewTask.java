package com.skye.todo.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skye.todo.Adapter.ToDoAdapter;
import com.skye.todo.Model.ToDoModel;
import com.skye.todo.OnDialogCloseListener;
import com.skye.todo.R;

public class AddNewTask extends BottomSheetDialogFragment {
    private TextInputEditText textInputEditText;
    private TextInputLayout textInputLayout;
    private MaterialButton saveButton, cancelButton;
    public static final String TAG = "AddNewTask";
    private DataBaseHelper myDB;
    int position;

    public static AddNewTask newInstance() {return new AddNewTask();}
    public AddNewTask(){}
    public AddNewTask(int position){this.position = position;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate( R.layout.add_task, container, false );
       return v;
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view , savedInstanceState );
        textInputEditText = view.findViewById( R.id.edit_text );
        textInputLayout = view.findViewById( R.id.edit_text_layout );
        saveButton = view.findViewById( R.id.save_button );
        cancelButton = view.findViewById( R.id.cancel_button );
        myDB = new DataBaseHelper( getActivity() );
        saveButton.setBackgroundColor( getResources().getColor( R.color.light_blue ) );
        saveButton.setTextColor( getResources().getColor( R.color.blue ) );
        textInputLayout.setBoxStrokeColor( getResources().getColor( R.color.light_blue ) );

        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            isUpdate = true;
            String task = bundle.getString( "task" );
            textInputEditText.setText( task );

            /*
            if(task.length() > 0)
            {
                saveButton.setEnabled( false );
                saveButton.setTextColor( getResources().getColor( R.color.grey ) );
            }

             */

        }
        /*
        textInputEditText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s , int start , int count , int after) {

            }

            @Override
            public void onTextChanged(CharSequence s , int start , int before , int count) {
               if(s.toString().equals( "" ))
               {

                   saveButton.setEnabled( false );
                   saveButton.setBackgroundColor( getResources().getColor( R.color.grey_800 ) );
                   saveButton.setTextColor( getResources().getColor( R.color.grey ) );

               }
               else
               {
                   saveButton.setEnabled( true );
                   saveButton.setBackgroundColor( getResources().getColor( R.color.light_blue ) );
                   saveButton.setTextColor( getResources().getColor( R.color.blue ) );
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        */
        updateDataBase();
        final boolean finalIsUpdate = isUpdate;
        saveButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textInputEditText.getText().toString();

                if(finalIsUpdate)
                {
                    ToDoAdapter.toDoModelList.get( position ).setTask( text );
                    //myDB.updateTask( bundle.getInt( "id" ), text );
                    updateDataBase();
                }
                else
                {
                    ToDoModel item = new ToDoModel();
                    item.setTask( text );
                    item.setStatus( 0 );
                    ToDoAdapter.toDoModelList.add(item);
                    updateDataBase();
                    //myDB.insertTask( item );
                }

                dismiss();
            }
        } );
        cancelButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss( dialog );
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseListener)
        {
            ((OnDialogCloseListener)activity).onDialogClose( dialog );
        }
    }
    private void updateDataBase()
    {
        //if(ToDoAdapter.dragged) {
            ToDoAdapter.myDB.db.delete( DataBaseHelper.TABLE_NAME , null , null );
            for (ToDoModel taskItem : ToDoAdapter.toDoModelList) {
                myDB.insertTask( taskItem );
            }
            //
       // }
    }
}
