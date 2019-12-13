package com.example.lyst.Models;

import com.example.lyst.util.AttachmentTypes;

public class CheckListDoItem {
    public boolean state;
    public String attachment;
    public String attchmentType;

    public CheckListDoItem(boolean state, String attachment, AttachmentTypes type) {
        this.state = state;
        this.attachment = attachment;
        this.attchmentType = type.name();
    }

    public boolean isChecked() {
        return state;
    }

    public String getAttachment() {
        return attachment;
    }
}
