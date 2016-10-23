/*
InputUsername.java
Shows an Input Dialog to the user to enter in their email address
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 2
Date Updated : 10/11/16
 */
package catchdot.live.com.catchthedot;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputUsername extends DialogFragment {

    private EditText emailtv;
    private static String dialogTitle;
    public InputUsername() {
        // Required empty public constructor
    }
    //**************************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_username, container, false);
        emailtv = (EditText) view.findViewById(R.id.edtEmail);
        Button saveEmailbtn = (Button) view.findViewById(R.id.btnSave);
        saveEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!emailtv.getText().toString().isEmpty())
                {
                    //---gets the calling activity---
                    if(emailtv.getText().toString().contains("@") && emailtv.getText().toString().contains("."))
                    {
                        InputNameDialogListener activity = (InputNameDialogListener) getActivity();
                        activity.onFinishInputDialog(emailtv.getText().toString());
                        dismiss();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), R.string.email_insert_valid_address,Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.error_insert_email,Toast.LENGTH_LONG).show();
                }
            }
        });

        //show the keyboard automatically
        emailtv.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //set the title for the dialog
        getDialog().setTitle(dialogTitle);

        return view;
    }
   // ***************************************************************
    //Interface containing methods to be implemented
    // by calling activity
    public interface InputNameDialogListener
   {
        void onFinishInputDialog(String inputText);
    }
    //***************************************************************
    //set the title of the dialog window
    public void setDialogTitle(String title) {
        dialogTitle = title;
    }
    //***************************************************************
}
