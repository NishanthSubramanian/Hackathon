package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class ExploreBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_base);
        CardView informalCard= findViewById(R.id.informalCard);
        CardView formalCard= findViewById(R.id.formalCard);

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
