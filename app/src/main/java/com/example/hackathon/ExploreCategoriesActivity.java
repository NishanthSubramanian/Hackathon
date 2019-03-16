package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExploreCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String type = i.getStringExtra("type");
        if (type.equals("I")) {
            InformalCategoriesAdapter ICA;
            setContentView(R.layout.activity_informal_categories);
            ArrayList<String> categories = new ArrayList<String>();

            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.informal_categories_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ICA = new InformalCategoriesAdapter(this, categories);
            recyclerView.setAdapter(ICA);
            recyclerView.setHasFixedSize(false);
            categories = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.informalCategoriesArray)));
            for (int j = 0; j < categories.size(); j++) {
                Log.d("HEY", categories.get(j));
                ICA.added(categories.get(j));

            }
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
        }
    }
}
