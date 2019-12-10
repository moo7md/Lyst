package com.example.lyst.util.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.example.lyst.R;
import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;

public class Dialogs {

    public static AlertDialog ImageDialog(Activity activity, ChecklistItemDoViewHolder caller) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage("Attach your image here");

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    public static AlertDialog PlaintextDialog(Activity activity, ChecklistItemDoViewHolder caller) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.attach_plaintext_alert_dialog, null));


        builder.setNegativeButton("Cancel", null);
        builder.setNeutralButton("Delete", null);
        builder.setPositiveButton("Save", new AttachPlaintext(caller));

        return builder.create();
    }

    public static AlertDialog TimestampDialog(Activity activity, ChecklistItemDoViewHolder caller) {

        return null;
    }
}