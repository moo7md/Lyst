package com.example.lyst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static String uid;
    private static Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uid = getIntent().getStringExtra("uid");
    }

    public void signUpMain(View v) {
        //check if user is signed up
        //if signed up, show error and ask to logout
        if (database.isLoggedIn()) {
            Intent i = new Intent(this, SignupActivity.class);
            i.putExtra("uid", uid);
            startActivity(i);
        }else{
            //show message
        }
    }
}
