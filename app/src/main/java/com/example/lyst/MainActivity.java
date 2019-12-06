package com.example.lyst;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
            Toast.makeText(this, R.string.loggedIn, Toast.LENGTH_LONG).show();
        }
    }

    public void loginMain(View v) {
        //check if user is signed up
        //if signed up, show error and ask to logout
        if (database.isLoggedIn()) {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("uid", uid);
            startActivityForResult(i, 1);
        }else{
            //show message
            Toast.makeText(this, R.string.loggedIn, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {// login
            if (data != null) {
                boolean loggedin = data.getBooleanExtra("loggedIn", false);
                if (loggedin) {
                    //start logged in user activity
                }else{
                    //don't no nuthing...!
                }
            }
        }
    }
}
