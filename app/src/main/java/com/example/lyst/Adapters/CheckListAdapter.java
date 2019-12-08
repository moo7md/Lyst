package com.example.lyst.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyst.Models.ChecklistItem;
import com.example.lyst.R;
import com.example.lyst.ViewHolders.CheckListHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListHolder> {

    private List<ChecklistItem> items;

    public CheckListAdapter(List<ChecklistItem> items){
        this.items = items;
    }

    @NonNull
    @Override
    public CheckListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        CheckListHolder myHolder = new CheckListHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListHolder holder, int position) {
        //do the stuff
        if (!items.isEmpty()) {
            ChecklistItem item = items.get(position);
            holder.checkBox.setText(item.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
