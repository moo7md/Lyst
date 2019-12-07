package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private Database database = Database.getInstance();
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        uid = getIntent().getStringExtra("uid");
    }

    public void signUp(View v) {
        //get textviews
        TextView emailTextView = findViewById(R.id.emailSignUp);
        TextView usernameTextView = findViewById(R.id.usernameSignUp);
        TextView passwordTextView = findViewById(R.id.passwordSignUp);

        //get the strings
        final String email = emailTextView.getText().toString();
        final String username = usernameTextView.getText().toString();
        final String password = passwordTextView.getText().toString();

        if (email.isEmpty()){
            emailTextView.setError("Please fill this field");
            return;
        }
        if (username.isEmpty()){
            usernameTextView.setError("Please fill this field");
            return;
        }
        if (password.isEmpty()){
            passwordTextView.setError("Please fill this field");
            return;
        }

        final Context context = this;

        database.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            database.db.collection("users").document(uid)
                                    .update("Email", email);
                            database.db.collection("users").document(uid)
                                    .update("username", username);
                            Toast.makeText(context, R.string.signedUp, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
        );
    }
    public void close(View v){
        finish();
    }
}
