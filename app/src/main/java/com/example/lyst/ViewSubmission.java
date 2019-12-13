package com.example.lyst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lyst.Adapters.CreateCheckListAdapter;
import com.example.lyst.Adapters.SubmissionAdapter;
import com.example.lyst.Models.CheckListDoItem;

import java.util.ArrayList;

public class ViewSubmission extends AppCompatActivity {

    private SubmissionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submission);

        RecyclerView recyclerView = findViewById(R.id.submission_recycler);
        adapter = new SubmissionAdapter(new ArrayList<CheckListDoItem>(){}, this);

        LinearLayoutManager lm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }
}
