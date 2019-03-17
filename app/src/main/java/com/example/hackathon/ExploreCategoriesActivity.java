package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class ExploreCategoriesActivity extends AppCompatActivity {

    private RecyclerView rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String type = i.getStringExtra("type");
        if (type.equals("I")) {
            InformalCategoriesAdapter ICA;
            setContentView(R.layout.activity_informal_categories);
            ArrayList<String> categories = new ArrayList<String>();

            final RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.informal_categories_rv);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            ICA = new InformalCategoriesAdapter(this, categories);
            recyclerView2.setAdapter(ICA);
            recyclerView2.setHasFixedSize(false);
            categories = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.informalCategoriesArray)));
            for (int j = 0; j < categories.size(); j++) {
                Log.d("HEY", categories.get(j));
                ICA.added(categories.get(j));
            }
            SearchView searchView=findViewById(R.id.informal_categories_sv);
            ArrayList<Event> E=new ArrayList<Event>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    rl=findViewById(R.id.informal_categories_rv);
                    rl.setVisibility(View.GONE);
                    SearchResultsAdapter SRA;
                    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.informal_categories_search_result_rv);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    SRA=new SearchResultsAdapter(getApplicationContext(),E);
                    recyclerView.setAdapter(SRA);
                    recyclerView.setHasFixedSize(false);
                    CollectionReference eventRef = db.collection("events");
                    eventRef.whereEqualTo("category", s).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
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
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
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
                    if (s.equals("") && rl != null) {
                        E.clear();
                        rl.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
        } else {
            FormalCategoriesAdapter FCA;
            setContentView(R.layout.activity_formal_categories);
            ArrayList<String> categories = new ArrayList<String>();

            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.formal_categories_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FCA = new FormalCategoriesAdapter(this, categories);
            recyclerView.setAdapter(FCA);
            recyclerView.setHasFixedSize(false);
            categories = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.formalCategoriesArray)));
            for (int j = 0; j < categories.size(); j++) {
                Log.d("HEY", categories.get(j));
                FCA.added(categories.get(j));
            }
            SearchView searchView=findViewById(R.id.formal_categories_sv);
            ArrayList<Event> E=new ArrayList<Event>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    rl=findViewById(R.id.formal_categories_rv);
                    rl.setVisibility(View.GONE);
                    SearchResultsAdapter SRA;
                    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.formal_categories_search_result_rv);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    SRA=new SearchResultsAdapter(getApplicationContext(),E);
                    recyclerView.setAdapter(SRA);
                    recyclerView.setHasFixedSize(false);
                    CollectionReference eventRef = db.collection("events");
                    eventRef.whereEqualTo("category", s).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
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
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
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
                    if (s.equals("") && rl != null) {
                        E.clear();
                        rl.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
        }
    }
}
