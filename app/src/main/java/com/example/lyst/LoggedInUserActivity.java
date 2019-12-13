package com.example.lyst;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
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


    private Database database;
    private String uid;
    private SQLiteDatabase sqldb;
    Fragment myList;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);

        LystSQLiteHelper lsql = new LystSQLiteHelper(this);
        sqldb = lsql.getWritableDatabase();
        setUID();
        database = Database.getInstance();

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
                    i.putExtra("type", page);
                    startActivityForResult(i, page);
//                    ((MyLists)getSupportFragmentManager().findFragmentById(R.id.myListFragment)).add("");
                }else{
                    //open global items...
                    Intent i = new Intent(v.getContext(), ListSearch.class);
                    i.putExtra("uid", uid);
                    i.putExtra("type", page);
                    startActivityForResult(i, page);
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
        FloatingActionButton btn = findViewById(R.id.addBtn);
        if (page == 0){
            return;
        }
        page = 0;
        btn.show();
        ((TextView)findViewById(R.id.title)).setText(R.string.myList);
        myList = MyLists.newInstance(uid);
        openFragment(myList);
    }
    public void showFollowed(View v) {
        FloatingActionButton btn = findViewById(R.id.addBtn);
        if (page == 1) {
            return;
        }
        page = 1;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case 0:
                    String item = data.getStringExtra("itemID");
                    ((MyLists) getSupportFragmentManager().
                            findFragmentById(R.id.box)).add(item);
                    break;
                case 1:
                    ArrayList<String> newFollowed = data.getStringArrayListExtra("newFollowed");
                    for (String s : newFollowed) {
                        ((Followed) getSupportFragmentManager().
                                findFragmentById(R.id.box)).add(s);
                    }
                    break;
            }
        }
    }
}
