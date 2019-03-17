package com.example.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ExploreBaseActivity extends AppCompatActivity implements AllEventsFragment.OnFragmentInteractionListener,
        GoingEventsActivity.OnFragmentInteractionListener, PastEventsActivity.OnFragmentInteractionListener, SavedEventsActivity.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private SearchView searchView;
    private LinearLayout ll;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i= new Intent(getApplicationContext(),InformalHomeActivity.class);
                    finish();
                    return true;
                case R.id.navigation_explore:
                    return true;
                case R.id.navigation_chats:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_base);
        CardView informalCard= findViewById(R.id.informalCard);
        User user = (User)getIntent().getExtras().get("informal");
        CardView formalCard= findViewById(R.id.formalCard);
        searchView=findViewById(R.id.explore_base_sv);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ArrayList<Event> E=new ArrayList<Event>();
        SearchResultsAdapter SRA;
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.explore_base_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SRA=new SearchResultsAdapter(this,E,user);
        recyclerView.setAdapter(SRA);
        recyclerView.setHasFixedSize(false);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ll=findViewById(R.id.explore_base_ll);
                ll.setVisibility(View.GONE);
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
                if (s.equals("") && ll != null) {
                    E.clear();
                    ll.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        informalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),ExploreCategoriesActivity.class);
                i.putExtra("type","I");
                Intent j=getIntent();
                User user = (User)getIntent().getExtras().getSerializable("informal");
                i.putExtra("informal",user);
                startActivity(i);
            }
        });

        formalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),ExploreCategoriesActivity.class);
                i.putExtra("type","F");
                Intent j=getIntent();
                User user = (User)getIntent().getExtras().getSerializable("informal");
                i.putExtra("informal",user);
                startActivity(i);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
