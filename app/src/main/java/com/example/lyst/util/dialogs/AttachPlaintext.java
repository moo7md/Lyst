package com.example.lyst.util.dialogs;

import android.content.DialogInterface;

import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;

public class AttachPlaintext implements DialogInterface.OnClickListener {
    private ChecklistItemDoViewHolder caller;
    AttachPlaintext(ChecklistItemDoViewHolder caller) {
        this.caller = caller;
    }
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}