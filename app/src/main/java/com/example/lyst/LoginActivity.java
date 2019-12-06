package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    private Database database;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uid = getIntent().getStringExtra("uid");
        database = Database.getInstance();
    }

    public void login(View v){
        TextView emailTextView = findViewById(R.id.emailLogin);
        final TextView passwordTextView = findViewById(R.id.passwordLogin);

        String email = emailTextView.getText().toString();
        final String pass = passwordTextView.getText().toString();

        if (email.isEmpty()) {
            emailTextView.setError("Please fill this field");
            return;
        }
        if (pass.isEmpty()) {
            passwordTextView.setError("Please fill this field");
            return;
        }

        final Context context = this;
        database.auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent data = new Intent();
                            data.putExtra("loggedIn", true);
                            setResult(1, data);
                            finish();
                        }else{
                            Toast.makeText(context, R.string.failedLogin, Toast.LENGTH_LONG).show();
                            passwordTextView.setText("");
                            Intent data = new Intent();
                            data.putExtra("loggedIn", true);
                            setResult(1, data);
                        }
                    }
                }
        );
    }
    public void close(View v) {
        finish();
    }
}
