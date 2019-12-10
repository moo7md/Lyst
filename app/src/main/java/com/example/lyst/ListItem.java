package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lyst.Adapters.DoCheckListAdapter;
import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class ListItem extends AppCompatActivity {


    //
    String uid, type;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        database = Database.getInstance();
        uid = getIntent().getStringExtra("uid");

        RecyclerView recyclerView = findViewById(R.id.ListItemRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(new DoCheckListAdapter());
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
}
