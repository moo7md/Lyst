package com.example.lyst;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lyst.Adapters.ListAdapter;
import com.example.lyst.Adapters.SubmissionAdapter;
import com.example.lyst.Models.CheckListDoItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Inbox extends Fragment {

    private Database database = Database.getInstance();
    private ListAdapter adapter;
    private String uid;
    private List<String> itemIDs;

    private Inbox(String uid) {
        this.uid = uid;
        itemIDs = new ArrayList<>();

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.inbox, container, false);

        ListView lv = v.findViewById(R.id.myInboxListView);
        adapter = new ListAdapter(getContext(), R.layout.checklist_item,
                (ArrayList<String>) itemIDs, uid, 1);
        lv.setAdapter(adapter);
        getInboxItems(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(parent.getContext(), ViewSubmission.class);
                i.putExtra("uid", uid);
                i.putExtra("submissionId", itemIDs.get(position));
                startActivity(i);
            }
        });

        return v;
    }

    private void getInboxItems(final ListView lv) {
//        database.db.collection("users").document(uid)
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()) {
//                    ArrayList<String> stuff = (ArrayList<String>) task.getResult().get("lists");
//                    assert stuff != null;
//                    for(String s : stuff) {
//                        itemIDs.add(s);
//                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();
//                    }
//                }
//            }
//        });
    }

    static Inbox newInstance(String uid) {

        Bundle args = new Bundle();

        Inbox fragment = new Inbox(uid);
        fragment.setArguments(args);
        return fragment;
    }
}
