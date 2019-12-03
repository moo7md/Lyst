package com.example.lyst;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

public class LystSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "user";
    public static final String COL_UID = "uid";
    private static final int VERSION = 1;

    private static final String CREATE_COMD =
            "CREATE TABLE "+TABLE_NAME+"(_id "+"INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +COL_UID+" TEXT NOT NULL);";

    public LystSQLiteHelper(Context context) {
        super(context,"lyst_db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMD);
        UUID uuid = UUID.randomUUID();
        ContentValues cv = new ContentValues(1);
        cv.put(COL_UID, uuid.toString());
        db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
