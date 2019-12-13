package com.example.lyst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lyst.Adapters.DoCheckListAdapter;
import com.example.lyst.Models.CheckListDo;
import com.example.lyst.Models.CheckListDoItem;
import com.example.lyst.Models.CheckListTemplate;
import com.example.lyst.Models.ChecklistTemplateItem;
import com.example.lyst.ViewHolders.ChecklistItemDoViewHolder;
import com.example.lyst.util.AttachmentTypes;
import com.example.lyst.util.dialogs.Dialogs;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class ListItem extends AppCompatActivity {


    //
    String uid, itemID;
    int type;
    Database database;
    private DoCheckListAdapter adapter;
    private StorageReference storage = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        database = Database.getInstance();
        uid = getIntent().getStringExtra("uid");
        itemID = getIntent().getStringExtra("itemID");
        type = getIntent().getIntExtra("type", 0);
        final FloatingActionButton btn = findViewById(R.id.markAsChecked);

        if (type != 1) btn.hide();

        final Context context = this;
        String collection = decideWhatCollection();
        String document = checkCorrectDocument();
        if (collection == null) return;
        final Activity This = this;
        database.db.collection(collection).document(document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    RecyclerView recyclerView = findViewById(R.id.ListItemRecycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    final String owner = task.getResult().getString("owner");
                    ArrayList<Map> items = (ArrayList<Map>) task.getResult().get("items");
                    final ArrayList<ChecklistTemplateItem> tasks = new ArrayList<>();
                    for (Map item : items) {
                        tasks.add(new ChecklistTemplateItem(
                                (String) item.get("title"), (String) item.get("desc"),
                                (String) item.get("attachmentType")
                        ));
                    }
                    adapter = new DoCheckListAdapter(new CheckListTemplate(
                            owner, itemID, tasks
                    ), This);
                    recyclerView.setAdapter(adapter);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateMarks(owner, tasks);
                            finish();
                        }
                    });
                }else{
                    System.out.println("NOT WORKING");
                }
            }
        });
    }

    private void updateMarks(String owner, ArrayList<ChecklistTemplateItem> tasks) {
        ArrayList<CheckListDoItem> items = new ArrayList<>();
        CheckListDo item;
        for (int i = 0; i < tasks.size(); i++) {
            items.add(new CheckListDoItem(adapter.getStateOfChild(i),
                    adapter.items.get(i).attachment,
                    AttachmentTypes.valueOf(adapter.items.get(i).attchmentType.toUpperCase())));
        }
        item = new CheckListDo(itemID, Timestamp.now(), uid, items, owner);
        database.markChecked(item);
    }

    private String checkCorrectDocument() {
        switch (type) {
            case 0: case 3: return itemID;
            case 1:         return (itemID.contains(uid)? itemID : itemID+uid);
        }
        return null;
    }

    private String decideWhatCollection() {
        switch (type) {
            case 0: case 3: return "temp";
            case 1:         return "followed";
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get the data back for pictures
        if (resultCode == RESULT_OK && data != null) {
            System.out.println(requestCode+" "+data.getData());
            if (requestCode == Dialogs.PICK_PHOTO && data.getData() != null) {
                //get the bitmap of the picked image
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    final int pos = data.getIntExtra("pos", 0);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] bytes = baos.toByteArray();
                    final StorageReference ref = storage.child("Images").child(itemID);
                    UploadTask uploadTask = ref.putBytes(bytes);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                adapter.items.get(pos).attachment = downloadUri.toString();
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
