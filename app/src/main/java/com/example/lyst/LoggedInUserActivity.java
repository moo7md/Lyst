package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;

public class LoggedInUserActivity extends AppCompatActivity {


    private List<ChecklistItem> items;
    private Database database;
    private String uid;
    private SQLiteDatabase sqldb;

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
        Fragment myList = MyLists.newInstance();
        openFragment(myList);
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
        ((TextView)findViewById(R.id.title)).setText(R.string.myList);
        Fragment myList = MyLists.newInstance();
        openFragment(myList);
    }
    public void showFollowed(View v) {
        ((TextView)findViewById(R.id.title)).setText(R.string.followed);
        Fragment myList = Followed.newInstance();
        openFragment(myList);
    }
    public void showSeen(View v) {
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
