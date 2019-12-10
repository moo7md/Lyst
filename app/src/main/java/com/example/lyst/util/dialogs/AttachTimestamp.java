package com.example.lyst.util.dialogs;

import android.content.DialogInterface;

import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;

public class AttachTimestamp implements DialogInterface.OnClickListener {
    private ChecklistItemDoViewHolder caller;

    AttachTimestamp(ChecklistItemDoViewHolder caller) {
        this.caller = caller;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
    }
}
