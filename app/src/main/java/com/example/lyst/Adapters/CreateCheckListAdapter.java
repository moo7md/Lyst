package com.example.lyst.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyst.Models.ChecklistTemplateItem;
import com.example.lyst.R;
import com.example.lyst.ViewHolders.ChecklistItemCreateViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreateCheckListAdapter extends RecyclerView.Adapter {

    private List<ChecklistTemplateItem> items;

    private ArrayList<ChecklistItemCreateViewHolder> viewHolders = new ArrayList<>();

    public CreateCheckListAdapter(List<ChecklistTemplateItem> _items) {
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void AddItem() {
        items.add(new ChecklistTemplateItem("T", "", ""));
//        notifyItemInserted(items.size()-1);
        notifyDataSetChanged();
    }

    public List<ChecklistItemCreateViewHolder> GetViewHolders() {
        return viewHolders;
    }

}
