package com.example.lyst;


public class ChecklistItem {
    private String id;
    private String title;
    private String desc;
    private String attachmentType;
    private String attachment;

    public ChecklistItem(String id, String title, String desc, String attachmentType, String attachment) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.attachmentType = attachmentType;
        this.attachment = attachment;
    }

    public String getId() {
        return id;
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

    public String getAttachment() {
        return attachment;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
