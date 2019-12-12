package com.example.lyst.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lyst.Database;
import com.example.lyst.ListItem;
import com.example.lyst.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ListAdapter extends ArrayAdapter<String> {


    String uid;
    int type;// types
             // 0 -> called from MyList
             // 1 -> called from Followed
             // 2 -> called from inbox
             // 3 -> called from ListSearch
    ArrayList<String> list;
    Database database;
    ConstraintLayout c;

    public ListAdapter(@NonNull Context context, int resource, ArrayList<String> list, String uid,
                       int type) {
        super(context, resource, list);
        this.uid = uid;
        this.list = list;
        this.type = type;
        database = Database.getInstance();
    }

    public ListAdapter(Context context, int resource, ArrayList<String> list,
                       String uid, int type, ConstraintLayout c) {
        super(context, resource, list);
        this.uid = uid;
        this.list = list;
        this.type = type;
        this.c = c;
        database = Database.getInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String item = getItem(position);
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
                int menuID = R.menu.list_menu;
                switch (type) {
                    case 2:
                        //reserved for later
                        break;
                    case 3:
                        menuID = R.menu.follow_menu;
                        break;
                }
                PopupMenu pop = new PopupMenu(getContext(), v);
                pop.getMenuInflater().inflate(menuID, pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                switch (type) {
                                    case 0:
                                        deleteMyTemp(position);
                                        break;
                                    case 1:
                                        deleteFollowed(position);
                                        break;
                                    case 2:
                                        //reserved for later
                                        break;
                                }
                                break;
                        }
                        return false;
                    }
                });
                pop.show();
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ListItem.class);
                i.putExtra("uid", uid);
                i.putExtra("itemID", item);
                v.getContext().startActivity(i);
            }
        });
        return convertView;
    }

    private void deleteFollowed(int position) {
        String item = getItem(position);
        database.deleteFollowed(item, uid);
        remove(item);
    }
    private void deleteMyTemp(int position) {
        String item = getItem(position);
        database.deleteMyTemp(item, uid);
        remove(item);
    }

    @Override
    public void add(@Nullable String object) {
        super.add(object);
        if (getCount() == 0 && c != null) {
            c.setVisibility(View.VISIBLE);
        }else if (c != null){
            c.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void remove(@Nullable String object) {
        list.remove(object);
        notifyDataSetChanged();
        if (getCount() == 0 && c != null) {
            c.setVisibility(View.VISIBLE);
        }else if (c != null){
            c.setVisibility(View.INVISIBLE);
        }
    }
}
