package com.example.lyst.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyst.Models.CheckListDoItem;
import com.example.lyst.Models.CheckListTemplate;
import com.example.lyst.Models.ChecklistTemplateItem;
import com.example.lyst.R;
import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoCheckListAdapter extends RecyclerView.Adapter<ChecklistItemDoViewHolder> {

    private CheckListTemplate template;
    private ArrayList<CheckListDoItem> items;
    private ArrayList<ChecklistItemDoViewHolder> viewHolders = new ArrayList<>();

    public DoCheckListAdapter (CheckListTemplate template) {
        this.template = template;
    }

    @NonNull
    @Override
    public ChecklistItemDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item_do, parent, false);

        ChecklistItemDoViewHolder myHolder = new ChecklistItemDoViewHolder(view);
        viewHolders.add(myHolder);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistItemDoViewHolder holder, int position) {
        if (!items.isEmpty()) {
            holder.attachListeners();
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}