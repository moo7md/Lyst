package com.example.lyst.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lyst.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChecklistItemDoViewHolder  extends RecyclerView.ViewHolder {

    public CheckBox checkBox;
    public TextView title;
    public TextView description;
    public ImageView hasAttachment;
    public Button addAttachment;
    public Button deleteAttachment;

    public ChecklistItemDoViewHolder(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.item_checkBox);
        hasAttachment = itemView.findViewById(R.id.hasAttachment_ImageView);
        addAttachment = itemView.findViewById(R.id.addAttachment_Button);
        deleteAttachment = itemView.findViewById(R.id.deleteAttachment_Button);
    }

    public void attachListeners() {
        checkBox.setOnCheckedChangeListener(new CheckboxListener());
        addAttachment.setOnClickListener(new AddAttachmentListener());
        deleteAttachment.setOnClickListener(new DeleteAttachmentListener());
    }

    class CheckboxListener implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        }
    }

    class AddAttachmentListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

        }
    }

    class DeleteAttachmentListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

        }
    }
}