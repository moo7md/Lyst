package com.example.lyst.Models;

public class CheckListDoItem {
    private boolean state;
    private String attachment;

    public CheckListDoItem(boolean state, String attachment) {
        this.state = state;
        this.attachment = attachment;
    }
}