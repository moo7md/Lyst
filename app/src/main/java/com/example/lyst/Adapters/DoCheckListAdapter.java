package com.example.lyst.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.lyst.Models.CheckListDoItem;
import com.example.lyst.Models.CheckListTemplate;
import com.example.lyst.Models.ChecklistTemplateItem;
import com.example.lyst.R;
import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;
import com.example.lyst.util.AttachmentTypes;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoCheckListAdapter extends RecyclerView.Adapter<ChecklistItemDoViewHolder> {

    private CheckListTemplate template;
    public ArrayList<CheckListDoItem> items;
    private ArrayList<ChecklistItemDoViewHolder> viewHolders = new ArrayList<>();
    private Activity parent;

    public DoCheckListAdapter (CheckListTemplate template, Activity parent) {
        this.template = template;
        this.items = new ArrayList<>(Collections.nCopies(template.tasks.size(),
                new CheckListDoItem(false, null, AttachmentTypes.NONE)));
        this.parent = parent;
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
    public void onBindViewHolder(@NonNull ChecklistItemDoViewHolder holder, final int position) {
        if (!items.isEmpty()) {
            ChecklistTemplateItem i = template.tasks.get(position);
            holder.title.setText(i.getTitle());
            holder.description.setText(i.getDesc());
            holder.attachListeners();
            holder.configure(i, parent, position, items);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    items.get(position).state = isChecked;
                }
            });
            viewHolders.add(holder);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public boolean getStateOfChild(int pos) {
        CheckBox state = viewHolders.get(pos).checkBox;
        return state.isChecked();
    }
    public ChecklistItemDoViewHolder getHolder(int pos){return viewHolders.get(pos);}
}
