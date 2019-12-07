package com.example.lyst;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChecklistCreateCheckListAdapter extends RecyclerView.Adapter {

    private List<ChecklistItem> items;

    private ArrayList<ChecklistItemCreateViewHolder> viewHolders = new ArrayList<>();

    public ChecklistCreateCheckListAdapter(List<ChecklistItem> _items) {
        items = _items;
    }

    @NonNull
    @Override
    public ChecklistItemCreateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item_create, parent, false);

        ChecklistItemCreateViewHolder holder = new ChecklistItemCreateViewHolder(view);
        viewHolders.add(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder = (ChecklistItemCreateViewHolder) holder;
        if (!items.isEmpty()) {
//            holder.itemView
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void AddItem() {
        items.add(new ChecklistItem(UUID.randomUUID().toString(), "T", "", "", ""));
//        notifyItemInserted(items.size()-1);
        notifyDataSetChanged();
    }

    public List<ChecklistItemCreateViewHolder> GetViewHolders() {
        return viewHolders;
    }

}
