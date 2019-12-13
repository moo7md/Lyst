package com.example.lyst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lyst.Adapters.CreateCheckListAdapter;
import com.example.lyst.Adapters.SubmissionAdapter;
import com.example.lyst.Models.CheckListDo;
import com.example.lyst.Models.CheckListDoItem;
import com.example.lyst.Models.CheckListTemplate;

import java.util.ArrayList;

public class ViewSubmission extends AppCompatActivity {

    private SubmissionAdapter adapter;
    private String uid, listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submission);

        TextView headerTextView = findViewById(R.id.submissionHeader_textView),
                listIdTextView = findViewById(R.id.listId_textView);

        Intent intent = getIntent();

        listId = intent.getStringExtra("submissionId");
        uid = intent.getStringExtra("uid");

        listIdTextView.setText(listId);

        CheckListDo submission = getSubmission();
        CheckListTemplate template = getTemplate(listId);

        headerTextView.setText(String.format(getString(R.string.submission_header), getUserName(), submission.date.toString()));

        RecyclerView recyclerView = findViewById(R.id.submission_recycler);
        adapter = new SubmissionAdapter(submission, template, this);

        LinearLayoutManager lm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);


    }


    // TODO: this
    private String getUserName() {

        return "Anonymous";
    }

    // TODO: this
    // maybe move this to CheckListDo
    private CheckListDo getSubmission() {
        return new CheckListDo(null, null, null, null, null);
    }

    // TODO: this
    // maybe move this to the template model
    private CheckListTemplate getTemplate(String templateId) {
        return null;
    }
}
