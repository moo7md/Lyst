package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.example.lyst.Adapters.CreateCheckListAdapter;
import com.example.lyst.Models.*;
import com.example.lyst.ViewHolders.ChecklistItemCreateViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CreateCheckListActivity extends AppCompatActivity {

    CreateCheckListAdapter adapter;
    List<ChecklistItem> items = new ArrayList<>();
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_check_list);

        database = Database.getInstance();

        RecyclerView recyclerView = findViewById(R.id.create_recycler);
        adapter = new CreateCheckListAdapter(items);

        LinearLayoutManager lm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    public void AddItem(View view) {
        items.add(new ChecklistItem("", "", ""));
        _addItem();
    }

    private void _addItem() {
        items.add(new ChecklistItem("", "", ""));
        adapter.notifyDataSetChanged();
    }

    public void Save(View view) {
        EditText listID = findViewById(R.id.listID);
        if (listID.getText().toString().isEmpty()) {
            listID.setError("Please fill this field");
        }else if (items.size() != 0){
            String listIDString = listID.getText().toString();
            int i = 0;
            for (ChecklistItemCreateViewHolder holder : adapter.GetViewHolders()) {
                items.get(i).setTitle(holder.title.getText().toString());
                items.get(i).setDesc(holder.description.getText().toString());
                i++;
            }
            database.addCheckListItems((ArrayList<ChecklistItem>) items, listIDString).addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                System.out.println("WORKED!");
                            } else {
                                Log.i("FIRE", task.getException().getLocalizedMessage());
                            }
                        }
                    }
            );
        }else {
            Toast.makeText(this, "Please put some items", Toast.LENGTH_LONG).show();
            _save();
        }
    }

    public void _save() {
        List<ChecklistItemCreateViewHolder> viewHolders = adapter.GetViewHolders();

        for (ChecklistItemCreateViewHolder i : viewHolders) {
            System.out.println(i.title.getText().toString());
            System.out.println(i.description.getText().toString());
            System.out.println("Position is: " + i.attachmentType.getCheckedRadioButtonId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_checklist_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.add_list_item:
                _addItem();
                break;
            case R.id.save_checklist:
                _save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
