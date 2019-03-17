package com.example.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformalHomeActivity extends AppCompatActivity implements AllEventsFragment.OnFragmentInteractionListener,
GoingEventsActivity.OnFragmentInteractionListener,PastEventsActivity.OnFragmentInteractionListener,SavedEventsActivity.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button createEvent;
    private User user;
    private CircleImageView profilePic;
    private Toolbar toolbar;
    private Menu menu;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informal_home);

        user = (User) getIntent().getExtras().get("informal");
        toolbar = findViewById(R.id.informal_home_tb);
        //toolbar.setTitle("");
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        viewPager = findViewById(R.id.informal_home_vp);
        tabLayout = findViewById(R.id.profile_tl);
        createEvent = findViewById(R.id.informal_btn_create_event);
        profilePic = findViewById(R.id.informal_profile_iv);

        if(! user.getImage().equals("null")){
            Glide.with(getApplicationContext()).load(user.getImage()).into(profilePic);
        }

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putSerializable("informal",user);
        AllEventsFragment allEventsFragment = new AllEventsFragment();
        SavedEventsActivity savedEventsActivity = new SavedEventsActivity();
        GoingEventsActivity goingEventsActivity = new GoingEventsActivity();
        PastEventsActivity pastEventsActivity = new PastEventsActivity();
        savedEventsActivity.setArguments(bundle);
        allEventsFragment.setArguments(bundle);
        goingEventsActivity.setArguments(bundle);
        pastEventsActivity.setArguments(bundle);
        viewPagerAdapter.addFragment(allEventsFragment,"All");
        viewPagerAdapter.addFragment(goingEventsActivity,"Going");
        viewPagerAdapter.addFragment(savedEventsActivity,"Saved");
        viewPagerAdapter.addFragment(pastEventsActivity,"Past");
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

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformalHomeActivity.this, ProfileActivity.class);
                intent.putExtra("informal",user);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //this.menu = menu;
        //menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_dashboard_black_24dp));
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            firebaseAuth.signOut();
           Intent intent = new Intent(InformalHomeActivity.this, LoginActivity.class);
           startActivity(intent);
           finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_explore:
                    Intent i=new Intent(getApplicationContext(),ExploreBaseActivity.class);
                    i.putExtra("informal",user);
                    startActivity(i);
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
