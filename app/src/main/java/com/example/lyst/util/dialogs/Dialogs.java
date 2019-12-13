package com.example.lyst.util.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lyst.Adapters.DoCheckListAdapter;
import com.example.lyst.R;
import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;

public class Dialogs {

    public final static int PICK_PHOTO = 10;

    public static AlertDialog ImageDialog(final Activity activity, ChecklistItemDoViewHolder caller, final int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage("Attach your image here");
        builder.setNeutralButton("Attach", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra("pos", position);
                activity.startActivityForResult(i, PICK_PHOTO);
            }
        });
        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    public static AlertDialog PlaintextDialog(Activity activity, final ChecklistItemDoViewHolder caller, Object o, Object attachment) {
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

    public static AlertDialog TimestampDialog(Activity activity, ChecklistItemDoViewHolder caller, int position) {

        return null;
    }
}