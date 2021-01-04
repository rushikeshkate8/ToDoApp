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
import com.google.android.material.textfield.TextInputEditText;
import com.skye.todo.Model.ToDoModel;
import com.skye.todo.OnDialogCloseListener;
import com.skye.todo.R;

public class AddNewTask extends BottomSheetDialogFragment {
    private TextInputEditText textInputEditText;
    private Button button;
    public static final String TAG = "AddNewTask";
    private DataBaseHelper myDB;

    public static AddNewTask newInstance()
    {
        return new AddNewTask();
    }
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
        button = view.findViewById( R.id.save_button );

        myDB = new DataBaseHelper( getActivity() );

        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            isUpdate = true;
            String task = bundle.getString( "task" );
            textInputEditText.setText( task );

            if(task.length() > 0)
            {
                button.setEnabled( false );
            }

        }
        textInputEditText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s , int start , int count , int after) {

            }

            @Override
            public void onTextChanged(CharSequence s , int start , int before , int count) {
               if(s.toString().equals( "" ))
               {
                   button.setEnabled( false );
                   button.setBackgroundColor( getResources().getColor( R.color.light_grey ) );
               }
               else
               {
                   button.setEnabled( true );
                   button.setBackgroundColor( getResources().getColor( R.color.blue ) );
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

        final boolean finalIsUpdate = isUpdate;
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textInputEditText.getText().toString();

                if(finalIsUpdate)
                {
                    myDB.updateTask( bundle.getInt( "id" ), text );
                }
                else
                {
                    ToDoModel item = new ToDoModel();
                    item.setTask( text );
                    item.setStatus( 0 );
                    myDB.insertTask( item );
                }

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
}
