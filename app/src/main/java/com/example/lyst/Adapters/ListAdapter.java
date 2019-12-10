package com.example.lyst.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lyst.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ListAdapter extends ArrayAdapter<String> {


    String uid;

    public ListAdapter(@NonNull Context context, int resource, ArrayList<String> list, String uid) {
        super(context, resource, list);
        this.uid = uid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.checklist_item, parent,
                    false);
        }

        TextView title = convertView.findViewById(R.id.listTitle);
        ImageButton btn = convertView.findViewById(R.id.listMenu);

        title.setText(item.replace(uid, ""));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pop = new PopupMenu(getContext(), v);
                pop.getMenuInflater().inflate(R.menu.list_menu, pop.getMenu());
                pop.show();
            }
        });
        return convertView;
    }
}
