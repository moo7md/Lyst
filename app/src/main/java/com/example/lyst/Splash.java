package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Splash extends AppCompatActivity {

    static SQLiteDatabase db;
    static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        database = Database.getInstance();

        LystSQLiteHelper lsql = new LystSQLiteHelper(this);
        db = lsql.getWritableDatabase();

        String uid;
        Cursor c = db.rawQuery("SELECT * FROM " + LystSQLiteHelper.TABLE_NAME, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            uid = c.getString(c.getColumnIndex(LystSQLiteHelper.COL_UID));
            inFirebase(uid);
        }
        c.close();
    }

    private void inFirebase(final String uid) {
        final Context context = this;
        database.db.collection("users").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful() && task.getResult().exists()) {
                            //open main
                            if(database.isLoggedIn()) {
                                Intent i = new Intent(context, LoggedInUserActivity.class);
                                i.putExtra("uid", uid);
                                context.startActivity(i);
                                finish();
                            }else{
                                Intent i = new Intent(context, MainActivity.class);
                                i.putExtra("uid", uid);
                                context.startActivity(i);
                                finish();
                            }
                        }else{
                            //put user in Firebase the open main
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("email", ""); //email of user
                            data.put("username", ""); //username for user
                            data.put("name", ""); //full name of user
                            data.put("lists", new ArrayList()); //the created checklists
                            data.put("shared", new ArrayList()); //shared checklist templates
                            data.put("temp", new ArrayList()); //created checklist templates

                            database.db.collection("users").document(uid)
                                    .set(data);
                            Intent i = new Intent(context, MainActivity.class);
                            i.putExtra("uid", uid);
                            context.startActivity(i);
                            finish();
                        }
                    }
                });
    }
}
