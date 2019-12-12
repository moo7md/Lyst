package com.example.lyst.ViewHolders;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.lyst.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChecklistItemCreateViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public EditText title;
    public EditText description;
    public RadioGroup attachmentType;

    public ChecklistItemCreateViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        title = itemView.findViewById(R.id.listTitleEditText);
        description = itemView.findViewById(R.id.listDescriptionEditText);
        attachmentType = itemView.findViewById(R.id.attachmentTypeRadioGroup);
    }
}
