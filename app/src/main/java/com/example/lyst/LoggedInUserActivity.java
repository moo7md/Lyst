package com.example.lyst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lyst.Models.CheckListTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class LoggedInUserActivity extends AppCompatActivity {


    private List<CheckListTemplate> items;
    private Database database;
    private String uid;
    private SQLiteDatabase sqldb;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);

        LystSQLiteHelper lsql = new LystSQLiteHelper(this);
        sqldb = lsql.getWritableDatabase();
        setUID();
        database = Database.getInstance();
        items = new ArrayList<>();

        LinearLayoutManager lm = new LinearLayoutManager(this);
        getItemsFromDatabase();

        //set fragment
        ((TextView)findViewById(R.id.title)).setText(R.string.myList);
        Fragment myList = MyLists.newInstance(uid);
        openFragment(myList);

        FloatingActionButton btn = findViewById(R.id.addBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page == 0) {
                    Intent i = new Intent(v.getContext(), CreateCheckListActivity.class);
                    i.putExtra("uid", uid);
                    startActivity(i);
                }else{
                    //open global items...
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loggedin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            database.auth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUID() {
        Cursor c = sqldb.rawQuery("SELECT * FROM "+LystSQLiteHelper.TABLE_NAME, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            uid = c.getString(c.getColumnIndex(LystSQLiteHelper.COL_UID));
        }
    }

    private void getItemsFromDatabase() {
        database.db.collection("items").whereEqualTo("owner", uid);
    }
    public void showMyList(View v) {
        if (page == 0) return;
        page = 0;
        FloatingActionButton btn = findViewById(R.id.addBtn);
        btn.show();
        ((TextView)findViewById(R.id.title)).setText(R.string.myList);
        Fragment myList = MyLists.newInstance(uid);
        openFragment(myList);
    }
    public void showFollowed(View v) {
        if (page == 1) return;
        page = 1;
        FloatingActionButton btn = findViewById(R.id.addBtn);
        btn.show();
        ((TextView)findViewById(R.id.title)).setText(R.string.followed);
        Fragment myList = Followed.newInstance(uid);
        openFragment(myList);
    }
    public void showSeen(View v) {
        if (page == 2) return;
        FloatingActionButton btn = findViewById(R.id.addBtn);
        btn.hide();
        page = 2;
        ((TextView)findViewById(R.id.title)).setText(R.string.seen);
        Fragment myList = Seen.newInstance();
        openFragment(myList);
    }

    private void openFragment(Fragment myList) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.box, myList);
        t.addToBackStack(null);
        t.commit();
    }
}
