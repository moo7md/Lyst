package com.example.lyst.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lyst.Models.CheckListDoItem;
import com.example.lyst.Models.ChecklistTemplateItem;
import com.example.lyst.R;
import com.example.lyst.util.AttachmentTypes;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SubmittedItemHolder extends RecyclerView.ViewHolder {

    TextView title, description, timestamp, shortAnswer, attachment;
    CheckBox status;
    ConstraintLayout pt, ts, img;
    Button imageButton;

    public SubmittedItemHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.taskTitle_textView);
        description = itemView.findViewById(R.id.taskDesctiption_textView);
        timestamp = itemView.findViewById(R.id.timestamp_textView);
        shortAnswer = itemView.findViewById(R.id.shortAnswer_textView);
        status = itemView.findViewById(R.id.item_checkBox);
        pt = itemView.findViewById(R.id.plaintext_attachment);
        ts = itemView.findViewById(R.id.timestamp_attachment);
        imageButton = itemView.findViewById(R.id.viewImage_button);
        attachment = itemView.findViewById(R.id.attachment_textView);
    }

    public void setup(ChecklistTemplateItem template, final CheckListDoItem item, final Context c) {
        title.setText(template.getTitle());
        description.setText(template.getDesc());
        status.setChecked(item.isChecked());

        switch (template.getAttachmentTypeEnum()) {
            case PLAINTEXT:
                pt.setVisibility(View.VISIBLE);
                shortAnswer.setText(item.getAttachment());
                break;
            case TIMESTAMP:
                ts.setVisibility(View.VISIBLE);
                timestamp.setText(item.getAttachment());
                break;
            case IMAGE:
                imageButton.setVisibility(View.VISIBLE);
                imageButton.setOnClickListener(new Button.OnClickListener (){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getAttachment()));
                        c.startActivity(intent);
                    }
                });
                break;
            case NONE:
                attachment.setVisibility(View.GONE);
        }

    }
}
