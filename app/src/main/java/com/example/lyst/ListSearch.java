package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lyst.Adapters.ListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;

public class ListSearch extends AppCompatActivity {


    String uid;
    Database database = Database.getInstance();
    ArrayList<String> temps = new ArrayList<>();
    ListAdapter la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);

        uid = getIntent().getStringExtra("uid");
        final ListView lv = findViewById(R.id.searchTemp);
        la  = new ListAdapter(this, R.layout.checklist_item,
                temps, uid, 3);
        lv.setAdapter(la);
        retriveAllTemp();

        final EditText search = findViewById(R.id.searchTempText);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(search.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void search(String toString) {
        if (toString.isEmpty()) {
            retriveAllTemp();
            return;
        }
        for(int i = 0; i < temps.size(); i++) {
            if (!temps.get(i).contains(toString)) {
                la.remove(temps.get(i));
            }
        }
    }
    private void retriveAllTemp() {
        temps.clear();
        database.db.collection("temp")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(DocumentSnapshot d : task.getResult().getDocuments()) {
                        temps.add(d.getId());
                        la.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
