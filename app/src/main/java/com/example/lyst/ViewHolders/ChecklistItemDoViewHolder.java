package com.example.lyst.ViewHolders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lyst.CreateCheckListActivity;
import com.example.lyst.Models.ChecklistTemplateItem;
import com.example.lyst.R;
import com.example.lyst.util.AttachmentTypes;
import com.example.lyst.util.dialogs.AttachImage;
import com.example.lyst.util.dialogs.AttachPlaintext;
import com.example.lyst.util.dialogs.AttachTimestamp;
import com.example.lyst.util.dialogs.Dialogs;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChecklistItemDoViewHolder  extends RecyclerView.ViewHolder {

    private Activity activity;
    public CheckBox checkBox;
    public TextView title;
    public TextView description;
    public ImageView hasAttachment;
    public Button addAttachment;

    public ChecklistItemDoViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.taskTitle_textView);
        description = itemView.findViewById(R.id.taskDesctiption_textView);
        checkBox = itemView.findViewById(R.id.item_checkBox);
        hasAttachment = itemView.findViewById(R.id.hasAttachment_ImageView);
        addAttachment = itemView.findViewById(R.id.addAttachment_Button);
    }

    public void attachListeners() {
        checkBox.setOnCheckedChangeListener(new CheckboxListener());
    }

    public void configure(ChecklistTemplateItem template, Activity activity) {
        this.activity = activity;
        configureButton (template.getAttachmentTypeEnum());
    }

    private void configureButton(AttachmentTypes type) {
        switch (type) {
            case NONE:
                addAttachment.setVisibility(View.GONE);
                hasAttachment.setVisibility(View.GONE);
                break;
            case PLAINTEXT:
                addAttachment.setOnClickListener(new AddAttachmentListener(Dialogs.PlaintextDialog(activity, this)));
                break;
            case IMAGE:
                addAttachment.setOnClickListener(new AddAttachmentListener(Dialogs.ImageDialog(activity, this)));
                break;
            case TIMESTAMP:
                addAttachment.setOnClickListener(new AddAttachmentListener(Dialogs.TimestampDialog(activity, this)));
                break;
        }
    }

    public void setHasAttachment(boolean succeeded, Object attachment) {

    }

    class CheckboxListener implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        }
    }

    class AddAttachmentListener implements Button.OnClickListener {

        AlertDialog dialog;

        AddAttachmentListener(AlertDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) {
            this.dialog.show();
        }
    }
}