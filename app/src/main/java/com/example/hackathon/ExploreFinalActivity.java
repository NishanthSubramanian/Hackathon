package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExploreFinalActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_final);
        Intent i= getIntent();
        String category=i.getStringExtra("category");
        ArrayList<Event>E=new ArrayList<Event>();
        SearchResultsAdapter SRA;
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.explore_final_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        SRA=new SearchResultsAdapter(getApplicationContext(),E);
        recyclerView.setAdapter(SRA);
        recyclerView.setHasFixedSize(false);
        CollectionReference eventRef = db.collection("events");
        eventRef.whereEqualTo("category", category).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("HEY", "Listen failed.");
                    return;
                }

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Event event=doc.toObject(Event.class);
                    Log.d("HEY", event.toString());
                    SRA.added(event);
                }
            }
        });
        RecyclerView rl=findViewById(R.id.explore_final_rv);
        SearchView searchView=findViewById(R.id.explore_final_sv);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                E.clear();
                if(rl!=null)
                    rl.setVisibility(View.GONE);
                SearchResultsAdapter SRA;
                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.explore_final_search_result_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                SRA=new SearchResultsAdapter(getApplicationContext(),E);
                recyclerView.setAdapter(SRA);
                recyclerView.setHasFixedSize(false);
                CollectionReference eventRef = db.collection("events");
                eventRef.whereEqualTo("category", s).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("HEY", "Listen failed.");
                            return;
                        }

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Event event=doc.toObject(Event.class);
                            Log.d("HEY", event.toString());
                            SRA.added(event);
                        }
                    }
                });
                eventRef.whereEqualTo("title", s).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("HEY", "Listen failed.");
                            return;
                        }

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Event event=doc.toObject(Event.class);
                            Log.d("HEY2", event.toString());
                            SRA.added(event);
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
