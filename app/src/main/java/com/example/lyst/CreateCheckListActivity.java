package com.example.lyst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateCheckListActivity extends AppCompatActivity {

    ChecklistCreateCheckListAdapter adapter;
    List<ChecklistItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_check_list);

        RecyclerView recyclerView = findViewById(R.id.create_recycler);
        adapter = new ChecklistCreateCheckListAdapter(items);

        LinearLayoutManager lm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    public void AddItem(View view) {
        items.add(new ChecklistItem(UUID.randomUUID().toString(), "", "", "", ""));
        adapter.notifyDataSetChanged();
    }

    public void Save(View view) {
        List<ChecklistItemCreateViewHolder> viewHolders = adapter.GetViewHolders();

        for (ChecklistItemCreateViewHolder i : viewHolders) {
            System.out.println(i.title.getText().toString());
            System.out.println(i.description.getText().toString());
            System.out.println("Position is: " + i.attachmentType.getCheckedRadioButtonId());
        }
    }
}
