package com.example.lyst;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class MyLists extends Fragment {

    private List<String> itemIDs;
    private String uid;
    private Database database = Database.getInstance();

    public MyLists(String uid) {
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
        View v = inflater.inflate(R.layout.my_lists, container, false);
        ListView lv = v.findViewById(R.id.myCheckListView);

        lv.setAdapter(new ListAdapter(getContext(), R.layout.checklist_item,
                (ArrayList<String>) itemIDs, uid));
        getTemplates(lv);
        return v;
    }

    private void getTemplates(final ListView lv) {
        database.db.collection("users").document(uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    ArrayList<String> stuff = (ArrayList<String>) task.getResult().get("temp");
                    assert stuff != null;
                    for(String s : stuff) {
                        itemIDs.add(s);
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public static MyLists newInstance(String uid) {

        Bundle args = new Bundle();

        MyLists fragment = new MyLists(uid);
        fragment.setArguments(args);
        return fragment;
    }
}

