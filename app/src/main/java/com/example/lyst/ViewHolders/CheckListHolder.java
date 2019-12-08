package com.example.lyst.ViewHolders;

import android.view.View;
import android.widget.CheckBox;

import com.example.lyst.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CheckListHolder extends RecyclerView.ViewHolder {

    public CheckBox checkBox;

    public CheckListHolder(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
}
