package com.example.lyst.Models;

import com.google.firebase.Timestamp;

import java.util.List;

public class CheckListDo {
    public String ListID;
    public Timestamp date;
    public String submitter;
    public String owner;
    public List<CheckListDoItem> items;


    public CheckListDo(String listID, Timestamp date, String submitter,
                       List<CheckListDoItem> items, String owner) {
        ListID = listID;
        this.date = date;
        this.submitter = submitter;
        this.items = items;
        this.owner = owner;
    }
}
