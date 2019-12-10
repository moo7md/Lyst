package com.example.lyst.util.dialogs;

import android.content.DialogInterface;

import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;

public class AttachImage implements DialogInterface.OnClickListener {

    ChecklistItemDoViewHolder caller;

    AttachImage(ChecklistItemDoViewHolder caller) {
        this.caller = caller;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}