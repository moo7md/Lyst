package com.example.lyst.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyst.Models.CheckListDoItem;
import com.example.lyst.Models.CheckListTemplate;
import com.example.lyst.Models.ChecklistTemplateItem;
import com.example.lyst.R;
import com.example.lyst.ViewHolders.CheckListHolder;
import com.example.lyst.ViewHolders.SubmittedItemHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubmissionAdapter extends RecyclerView.Adapter<SubmittedItemHolder> {

    private ArrayList<CheckListDoItem> items = new ArrayList<>();
    private CheckListTemplate template;

    private Context context;

    public SubmissionAdapter(ArrayList<CheckListDoItem> items, Context context) {
        this.items.addAll(items);
        this.context = context;
    }

    @NonNull
    @Override
    public SubmittedItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.submitted_item_line, parent, false);
        return new SubmittedItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubmittedItemHolder holder, int position) {
        //do the stuff
        if (!items.isEmpty()) {
            CheckListDoItem item = items.get(position);
            ChecklistTemplateItem templateItem = template.tasks.get(position);
            holder.setup(templateItem, item, context);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
