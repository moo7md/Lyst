package com.example.lyst.Models;


import com.example.lyst.util.AttachmentTypes;

public class ChecklistTemplateItem {
    private String title;
    private String desc;
    private String attachmentType;

    public ChecklistTemplateItem(String title, String desc, String attachmentType) {
        this.title = title;
        this.desc = desc;
        this.attachmentType = attachmentType;
    }
    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getAttachmentType() {
        return attachmentType;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public AttachmentTypes getAttachmentTypeEnum() {
        return AttachmentTypes.valueOf(attachmentType.toUpperCase());
    }
}
