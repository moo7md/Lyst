package com.example.lyst;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyLists extends Fragment {

    private List<String> itemIDs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_lists, container, false);
        ListView lv = v.findViewById(R.id.myCheckListView);

        lv.setAdapter(new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1,));
        return v;
    }

    public static MyLists newInstance() {

        Bundle args = new Bundle();

        MyLists fragment = new MyLists();
        fragment.setArguments(args);
        return fragment;
    }
}

