package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lyst.Adapters.CreateCheckListAdapter;
import com.example.lyst.Adapters.SubmissionAdapter;
import com.example.lyst.Models.CheckListDo;
import com.example.lyst.Models.CheckListDoItem;
import com.example.lyst.Models.CheckListTemplate;
import com.example.lyst.Models.ChecklistTemplateItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class ViewSubmission extends AppCompatActivity {

    private SubmissionAdapter adapter;
    private String uid, listId;
    Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submission);

        final TextView headerTextView = findViewById(R.id.submissionHeader_textView),
                listIdTextView = findViewById(R.id.listId_textView);

        final Intent intent = getIntent();

        listId = intent.getStringExtra("submissionId");
        System.out.println(listId);
        uid = intent.getStringExtra("uid");

        listIdTextView.setText(listId);

        final String name = intent.getStringExtra("name");
        final Context context = this;

        database.db.collection("submission").document(listId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    CheckListDo submission = new CheckListDo(listId, task.getResult().getTimestamp("date"), task.getResult().getString("submitter")
                            , (ArrayList<CheckListDoItem>) task.getResult().get("items"),task.getResult().getString("owner") );
                    System.out.println(task.getResult().contains("owner"));
                    System.out.println(task.getResult().contains("date"));
                    System.out.println(task.getResult().contains("ListID"));
                    System.out.println(task.getResult().contains("items"));
//                    CheckListTemplate template =  new CheckListTemplate(documentSnapshot1.getString("owner"), documentSnapshot1.getId(),
//                            (ArrayList<ChecklistTemplateItem>) documentSnapshot1.get("items"));
//                    headerTextView.setText(String.format(getString(R.string.submission_header), name, submission.date.toString()));
//
//                    RecyclerView recyclerView = findViewById(R.id.submission_recycler);
//                    adapter = new SubmissionAdapter(submission, template, context);
//
//                    LinearLayoutManager lm = new LinearLayoutManager(context);
//
//                    recyclerView.setLayoutManager(lm);
//                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

//    // TODO: this
//    // maybe move this to CheckListDo
//    private CheckListDo getSubmission() {
//        database.getSub(listId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//            }
//        });
//    }
//
//    // TODO: this
//    // maybe move this to the template model
//    private CheckListTemplate getTemplate(String templateId) {
//        return null;
//    }
}
