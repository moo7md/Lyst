package com.example.lyst.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lyst.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChecklistItemDoViewHolder  extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView description;
    public ImageView hasAttachment;
    public Button addAttachment;
    public Button deleteAttachment;

    public ChecklistItemDoViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.taskTitle_textView);
        description = itemView.findViewById(R.id.taskDesctiption_textView);
        hasAttachment = itemView.findViewById(R.id.hasAttachment_ImageView);
        addAttachment = itemView.findViewById(R.id.addAttachment_Button);
        deleteAttachment = itemView.findViewById(R.id.deleteAttachment_Button);
    }

    private void attachListeners() {

    }
}