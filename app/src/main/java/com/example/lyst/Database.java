package com.example.lyst;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.lyst.Models.*;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;


public class Database {

    public FirebaseAuth auth;
    public FirebaseFirestore db;

    private static Database database;

    private Database() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    /**
     * This function checks whether the current user is logged in or not
     *
     * @return true if user is logged in and false if not.
     */
    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    /**
     * Allows the user to login to the app using email and password
     *
     * @param email    email of the user
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
     *
     * @return null if user not logged in, otherwise an instance of the user 'FirebaseUser'
     */
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public Task<Void> addCheckListItems(ArrayList<ChecklistTemplateItem> items, String id, String uid) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("items", items);
        return db.collection("followed").document(id + uid).set(data);
    }

    public Task<Void> addChecklistTemp(ArrayList<ChecklistTemplateItem> items, String id, String uid) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("owner", uid);
        return db.collection("temp").document(id).set(data);
    }

    public Task<Void> updateUserInfo(String itemID, String uid) {
        return db.collection("users").document(uid)
                .update("temp", FieldValue.arrayUnion(itemID));
    }

    public Task<Void> deleteFollowed(String item, String uid) {
        db.collection("followed").document(item)
                .delete();
        return db.collection("users").document(uid)
                .update("lists", FieldValue.arrayRemove(item));
    }

    public Task<Void> deleteMyTemp(String item, String uid) {
        db.collection("temp").document(item)
                .delete();
        return db.collection("users").document(uid)
                .update("temp", FieldValue.arrayRemove(item));
    }

    public void follow(final String item, final String uid) {
        db.collection("temp").document(item)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    if (ds != null) {
                        ArrayList<Map> items = (ArrayList<Map>) ds.get("items");
                        Map<String, Object> map = new HashMap();
                        map.put("items", items);
                        db.collection("followed").document(item+uid).set(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                db.collection("users").document(uid)
                                        .update("lists", FieldValue.arrayUnion(item+uid));
                            }
                        });
                    }
                }
            }
        });
    }

    public void markChecked(CheckListDo item) {
        db.collection("submission").document(item.ListID+item.submitter)
                .set(item);
    }
    public Task<QuerySnapshot> getInbox(String uid){
         return db.collection("submission").whereEqualTo("owner", uid).get();
    }
    public Task<DocumentSnapshot> getSub(String listID){
        return db.collection("submission").document(listID).get();
    }
}
