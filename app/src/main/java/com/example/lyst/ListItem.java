package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.lyst.Adapters.DoCheckListAdapter;
import com.example.lyst.Models.CheckListTemplate;
import com.example.lyst.Models.ChecklistTemplateItem;
import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ListItem extends AppCompatActivity {


    //
    String uid, itemID;
    int type;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        database = Database.getInstance();
        uid = getIntent().getStringExtra("uid");
        itemID = getIntent().getStringExtra("itemID");
        type = getIntent().getIntExtra("type", 0);

        final Context context = this;
        String collection = decideWhatCollection();
        String document = checkCorrectDocument();
        if (collection == null) return;
        final Activity This = this;
        database.db.collection(collection).document(document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    RecyclerView recyclerView = findViewById(R.id.ListItemRecycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    String owner = "temp";
                    ArrayList<Map> items = (ArrayList<Map>) task.getResult().get("items");
                    ArrayList<ChecklistTemplateItem> tasks = new ArrayList<>();
                    for (Map item : items) {
                        tasks.add(new ChecklistTemplateItem(
                                (String) item.get("title"), (String) item.get("desc"),
                                (String) item.get("attachmentType")
                        ));
                    }
                    recyclerView.setAdapter(new DoCheckListAdapter(new CheckListTemplate(
                            owner, itemID, tasks
                    ), This));
                }else{
                    System.out.println("NOT WORKING");
                }
            }
        });
    }

    private String checkCorrectDocument() {
        switch (type) {
            case 0:     return itemID;
            case 1:     return (itemID.contains(uid)? itemID : itemID+uid);
        }
        return null;
    }

    private String decideWhatCollection() {
        switch (type) {
            case 0:     return "temp";
            case 1:     return "followed";
        }
        return null;
    }

    private void retriveItems() {
        database.db.collection("users").document(uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult() != null) {
                        ArrayList<String> list = (ArrayList<String>) task.getResult().get("list");
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get the data back for pictures
    }
}
