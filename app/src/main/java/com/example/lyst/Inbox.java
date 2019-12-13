package com.example.lyst;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Inbox extends Fragment {

    private Database database = Database.getInstance();
    private ArrayAdapter<String> adapter;
    private String uid;
    private List<String> itemIDs;
    private List<String> listTitles = new ArrayList<>();
    private List<String> usernames = new ArrayList<>();

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
        adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1);
        lv.setAdapter(adapter);
        getInboxItems(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(parent.getContext(), ViewSubmission.class);
                i.putExtra("uid", uid);
                i.putExtra("submissionId", itemIDs.get(position));
                i.putExtra("name", usernames.get(position));
                i.putExtra("title", listTitles.get(position));
                startActivity(i);
            }
        });

        return v;
    }

    // TODO: this
    private void getInboxItems(final ListView lv) {
        database.getInbox(uid).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(final DocumentSnapshot ds : task.getResult().getDocuments()) {
                        database.db.collection("users").document(ds.getString("submitter"))
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String name = documentSnapshot.getString("name");
                                if (name.isEmpty()) {
                                    adapter.add("Anonymous submitted " + ds.getString("ListID").replace(uid, ""));
                                    itemIDs.add(ds.getString("ListID"));
                                    listTitles.add(ds.getString("ListID").replace(uid, ""));
                                    usernames.add("Anonymous");
                                }else{
                                    adapter.add(name+ " submitted " + ds.getString("ListID").replace(uid, ""));
                                    itemIDs.add(ds.getString("ListID"));
                                    listTitles.add(ds.getString("ListID").replace(uid, ""));
                                    usernames.add(name);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    static Inbox newInstance(String uid) {

        Bundle args = new Bundle();

        Inbox fragment = new Inbox(uid);
        fragment.setArguments(args);
        return fragment;
    }
}
