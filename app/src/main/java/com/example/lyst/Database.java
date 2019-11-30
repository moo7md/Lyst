package com.example.lyst;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Database {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public Database database;

    private Database() {
        auth = FirebaseAuth.getInstance();
        db   = FirebaseFirestore.getInstance();
    }

    public Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return this.database;
    }

    /**
     * This function checks whether the current user is logged in or not
     * @return true if user is logged in and false if not.
     */
    public boolean isLoggedIn() {
        return auth.getCurrentUser() == null;
    }

    /**
     * Allows the user to login to the app using email and password
     * @param email email of the user
     * @param password password associated with the email
     * @return true if user logged in successfully false if login failed.
     */
    public boolean login(String email, String password) {
        if (!isLoggedIn()) {
            return auth.signInWithEmailAndPassword(email, password).isSuccessful();
        }
        return true;
    }

    /**
     * Gives back the user who is currently logged in.
     * @return null if user not logged in, otherwise an instance of the user 'FirebaseUser'
     */
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

//    public ArrayList<String> getChecklistItemsFromUser() {
//
//    }
}
