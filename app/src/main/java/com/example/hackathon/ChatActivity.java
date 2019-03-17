package com.example.hackathon;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
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
    private EditText edittext_chatbox;
    private ImageButton button_chatbox_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth  =FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.rv);
        edittext_chatbox = findViewById(R.id.edittext_chatbox);
        button_chatbox_send = findViewById(R.id.button_chatbox_send);


        //PRAJWAL PLEASE GET UID
        messageAdapter = new MessageAdapter(getApplicationContext(),new ArrayList<Message>(),firebaseAuth.getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(messageAdapter);
        //PRAJWAL PLEASE GET EVENT ID
        //eventId = "ok1JALzkGmMoGr2Dlyr4x1VMUyY2+1552761243488";

        firebaseFirestore.collection("events").document(eventId).collection("messages").orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener( new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                Message message = doc.getDocument().toObject(Message.class);
                                /*message.setText(doc.getDocument().get("text") + "");
                                message.setId(doc.getDocument().getId());*/
                                message.setId(doc.getDocument().getId());
                                message.compute();
                                Log.d("ADSd",message.toString());
                                messageAdapter.added(message);
                            }
                        }
                    }
                });

        button_chatbox_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = edittext_chatbox.getText().toString();
                HashMap<String, Object> map = new HashMap<>();
                map.put("text",msg);
                map.put("time",System.currentTimeMillis());
                //map.put("uid",firebaseAuth.getUid());
                firebaseFirestore.collection("events").document(eventId).collection("messages")
                        .document(String.valueOf(map.get("time"))+"+"+firebaseAuth.getUid())
                        .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }
        });
    }


}
