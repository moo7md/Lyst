package com.example.lyst.Models;

import java.util.ArrayList;

public class CheckListTemplate {
    public String owner;
    public String ID;
    public ArrayList<ChecklistTemplateItem> tasks;

    public CheckListTemplate(){}

    public CheckListTemplate(String owner, String listId, ArrayList<ChecklistTemplateItem> tasks) {
        this.owner = owner;
        this.ID = listId;
        this.tasks = tasks;
    }
}
