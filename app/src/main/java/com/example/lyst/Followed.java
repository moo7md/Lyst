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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Followed extends Fragment {

    private List<String> itemIDs;
    private String uid;
    private Database database;
    private ListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemIDs = new ArrayList<>();
        database = Database.getInstance();
        uid = getArguments().getString("uid");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.followed, container, false);
        ListView lv = v.findViewById(R.id.followedList);
        adapter = new ListAdapter(getContext(), R.layout.checklist_item,
                (ArrayList<String>) itemIDs, uid, 1);
        lv.setAdapter(adapter);
        getTemplates(lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(parent.getContext(), ListItem.class);
                i.putExtra("uid", uid);
                i.putExtra("itemID", itemIDs.get(position));
                i.putExtra("type", 1);
                startActivity(i);
            }
        });
        return v;
    }

    private void getTemplates(final ListView lv) {
        database.db.collection("users").document(uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    ArrayList<String> stuff = (ArrayList<String>) task.getResult().get("lists");
                    assert stuff != null;
                    for(String s : stuff) {
                        itemIDs.add(s);
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void add(String item) {
        adapter.add(item);
    }

    static Followed newInstance(String uid) {

        Bundle args = new Bundle();
        args.putString("uid", uid);
        Followed fragment = new Followed();
        fragment.setArguments(args);
        return fragment;
    }
}
