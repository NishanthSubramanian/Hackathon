package com.example.hackathon;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class EventDetailActivity extends AppCompatActivity {

    private TextView description, title, hostName, location, time;
    private Event event;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Event Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        description = findViewById(R.id.event_detail_description);
        title = findViewById(R.id.detail_event_title);
        hostName = findViewById(R.id.detail_event_host);
        time = findViewById(R.id.event_detail_time);
        location  = findViewById(R.id.event_detail_location);

        event = (Event) getIntent().getExtras().get("event");

        description.setText(event.getDescription());
        title.setText(event.getTitle());
        hostName.setText(event.getHostName());
        location.setText(event.getLocation());
        time.setText(event.getStartDate());

        recyclerView = findViewById(R.id.event_detail_rv);
        ParticipantAdapter participantAdapter = new ParticipantAdapter(getApplicationContext(),new ArrayList<User>());

        firebaseFirestore.collection("events").document(event.getEventId())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        Event event = documentSnapshot.toObject(Event.class);

                        for(String s : event.getParticipantsId()){
                            firebaseFirestore.collection("informal").document(s)
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User user = documentSnapshot.toObject(User.class);
                                    participantAdapter.added(user);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }

                    }
                });

    }
}
