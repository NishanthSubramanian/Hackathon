package com.example.hackathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventActivity extends AppCompatActivity {
    private TextView event_name;
    private TextView event_desc;
    private TextView start_date;
    private TextView end_date;
    private TextView location;
    private TextView noOfParticipants;
    private Event event;
    private CircleImageView circleImageView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        event_name = findViewById(R.id.event_name);
        event_desc = findViewById(R.id.event_desc);
        start_date = findViewById(R.id.start_date);
//        end_date = findViewById(R.id.end_date);
        location = findViewById(R.id.location);
        noOfParticipants = findViewById(R.id.noOfParticipants);
        circleImageView = findViewById(R.id.circleImageView);
        event = (Event)getIntent().getExtras().getSerializable("event");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        event_name.setText(event.getTitle());
        event_desc.setText(event.getDescription());
        start_date.setText(event.getStartDate().toString());
//        end_date.setText(event.getEndDate().toString());
        location.setText(event.getLocation());
//        noOfParticipants.setText(event.getParticipants().size() + " are going");

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("events").document(event.getEventId())
                        .update("participantId",FieldValue.arrayUnion(firebaseAuth.getUid()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                firebaseFirestore.collection("informal").document(firebaseAuth.getUid())
                                .update("going",FieldValue.arrayUnion(event.getEventId()))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(EventActivity.this,ChatActivity.class);
                                        intent.putExtra("event",event);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

    }
}
