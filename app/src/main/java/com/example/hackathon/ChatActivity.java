package com.example.hackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;


public class ChatActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String eventId;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth  =FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.rv);
        messageAdapter = new MessageAdapter(getApplicationContext(),new ArrayList<Message>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(messageAdapter);

        eventId = "wmgCJ6BgmiTBe0lyAprl";

        firebaseFirestore.collection("events").document(eventId).collection("messages")
                .addSnapshotListener( new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                Message message = new Message();
                                message.setText(doc.getDocument().get("text").toString());
                                message.setUid(doc.getDocument().getId());
                                Log.d("ADSd",message.toString());
                                messageAdapter.added(message);
                            }
                        }
                    }
                });

//        firebaseFirestore.collection("events").document(eventId)
//                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//
//                        if(documentSnapshot != null)
//                        {
//                            Log.d("Mesg",documentSnapshot.get("message").toString());
//                            HashMap<String,String> map = (HashMap<String, String>) documentSnapshot.get("message");
//                            for (String key : map.keySet()) {
//                                Log.d("KEy",key + " " + map.get(key) + "");
//                                Message message = new Message();
//                                message.setText(map.get(key));
//                                message.setUid(key);
//                                messageAdapter.added(message);
//                            }
//                            //                            Log.d("Mesg",documentSnapshot.get("message"));
//                        }
//                    }
//                });
    }


}
