package com.example.lyst.util.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lyst.R;
import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;

public class Dialogs {

    public static AlertDialog ImageDialog(Activity activity, ChecklistItemDoViewHolder caller) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage("Attach your image here");

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    public static AlertDialog PlaintextDialog(Activity activity, final ChecklistItemDoViewHolder caller, Object attachment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        final View view = inflater.inflate(R.layout.attach_plaintext_alert_dialog, null);
        final EditText field = view.findViewById(R.id.attach_text_editText);

        if (attachment != null) field.setText((String) attachment);

        builder.setView(view);
        builder.setNegativeButton("Cancel", null);

        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                caller.setAttachment(false, null);
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                caller.setAttachment(true, field.getText());
            }
        });

        return builder.create();
    }

    public static AlertDialog TimestampDialog(Activity activity, ChecklistItemDoViewHolder caller) {

        return null;
    }
}