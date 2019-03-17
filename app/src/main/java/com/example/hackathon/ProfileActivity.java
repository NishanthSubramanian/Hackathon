package com.example.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private User user;
    private TextView tvProfileEmail;
    private TextView tvProfileContact ;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView year;
    private Button btnEditDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth =  FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        user = (User) getIntent().getExtras().getSerializable("informal");
        Log.d("PRO",user.toString());
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileContact = findViewById(R.id.tvProfileContact);
        year = findViewById(R.id.year);

        tvProfileEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        tvProfileContact.setText(user.getPhone().toString());
        year.setText(user.getYear());
        btnEditDetails = findViewById(R.id.btnEditDetails);
        btnEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SetupActivity.class);
                intent.putExtra("from","profile");
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

    }


}
