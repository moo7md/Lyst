package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signUp(View v) {
        //get textviews
        TextView emailTextView = findViewById(R.id.emailSignUp);
        TextView usernameTextView = findViewById(R.id.usernameSignUp);

        //get the strings
        String email = emailTextView.getText().toString();
        String username = usernameTextView.getText().toString();

        ActionCodeSettings ACS = ActionCodeSettings.newBuilder()
                .setUrl("https://lyst.page.link/zXbp")
                .setHandleCodeInApp(true)
                .setAndroidPackageName(
                        "https://lyst.page.link/zXbp",
                        true,
                        "12"
                )
                .setDynamicLinkDomain("https://lyst.page.link/zXbp")
                .build();
        Log.i("DEBUGSIGNUP", ACS.getUrl());
        if (!auth.isSignInWithEmailLink(email)) {
            auth.signInWithEmailLink(email, ACS.getUrl())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("DEBUGSIGNUP", "worked");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
