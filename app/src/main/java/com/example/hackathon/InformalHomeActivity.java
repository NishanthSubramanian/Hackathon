package com.example.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InformalHomeActivity extends AppCompatActivity implements AllEventsFragment.OnFragmentInteractionListener,
GoingEventsActivity.OnFragmentInteractionListener,PastEventsActivity.OnFragmentInteractionListener,SavedEventsActivity.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button createEvent;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informal_home);

        user = (User) getIntent().getExtras().get("informal");

        viewPager = findViewById(R.id.informal_home_vp);
        tabLayout = findViewById(R.id.profile_tl);
        createEvent = findViewById(R.id.informal_btn_create_event);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AllEventsFragment(),"All");
        viewPagerAdapter.addFragment(new GoingEventsActivity(),"Going");
        viewPagerAdapter.addFragment(new SavedEventsActivity(),"Saved");
        viewPagerAdapter.addFragment(new PastEventsActivity(),"Past");
        viewPager.setOffscreenPageLimit(4);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformalHomeActivity.this,AddEventActivity.class);
                intent.putExtra("informal", user);
                startActivity(intent);
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
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
    public void onFragmentInteraction(Uri uri) {

    }
}
