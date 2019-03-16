package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

public class ExploreBaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_base);
        CardView informalCard= findViewById(R.id.informalCard);
        CardView formalCard= findViewById(R.id.formalCard);
        searchView=findViewById(R.id.explore_base_sv);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ll=findViewById(R.id.explore_base_ll);
                ll.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        informalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),ExploreCategoriesActivity.class);
                i.putExtra("type","I");
                startActivity(i);
            }
        });

        formalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),ExploreCategoriesActivity.class);
                i.putExtra("type","F");
                startActivity(i);
            }
        });
    }
}
