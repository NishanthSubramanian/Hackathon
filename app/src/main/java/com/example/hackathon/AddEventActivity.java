package com.example.hackathon;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddEventActivity extends AppCompatActivity {
    private TextInputLayout dateTime,title, place,description;
    private TextInputEditText dateTimeEdit;
    private Toolbar toolbar;
    private Button createEvent;
    private Spinner spinner;
    private Event event;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String st = "";
    private User  user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.event_tb);
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

        user = (User) getIntent().getExtras().get("informal");

        dateTime = findViewById(R.id.add_event_date_time_til);
        title = findViewById(R.id.add_event_title_til);
        place = findViewById(R.id.add_event_location_til);
        description = findViewById(R.id.add_event_description_til);
        createEvent = findViewById(R.id.event_add_event_btn);
        spinner = findViewById(R.id.add_event_category_spinner);


        dateTimeEdit = findViewById(R.id.add_event_date_time_tiet);
        dateTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateTime(v);
                Log.d("BRO","CLICKED");
            }
        });

        /*dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateTime(v);
                Log.d("BRO", "CLICKED");
            }
        });*/

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event = new Event();
                String problem_description = description.getEditText().getText().toString();
                String location = place.getEditText().getText().toString();
                String title_string = title.getEditText().getText().toString();
                String time = dateTime.getEditText().getText().toString();
                String category = spinner.getSelectedItem().toString();

                if (problem_description.length() == 0 || location.length() == 0 || title_string.length() == 0 || time.length() == 0) {
                    if (problem_description.length() == 0) {
                        description.setError("Please enter a description before submitting");
                    }
                    if (location.length() == 0) {
                        place.setError("Please enter amount before submitting");
                    }
                    if (title_string.length() == 0) {
                        title.setError("Please enter title before submitting");
                    }
                    if (time.length() == 0) {
                        dateTime.setError("Please enter number before submitting");
                    }

                }else{
                    event.setCategory(category);
                    event.setDescription(problem_description);
                    event.setLocation(location);
                    event.setTitle(title_string);
                    event.setStartDate(time);
                    event.setEventId(firebaseAuth.getUid()+"+"+String.valueOf(System.currentTimeMillis()));
                    event.setCreationTime(System.currentTimeMillis());
                    event.setFormal(false);
                    event.setHostImage(user.getImage());
                    event.setHostName(user.getName());
                    sendToFirebase();
                }
            }
        });

    }

    private void sendToFirebase() {
        Log.d("tag", "sending to firebase");
        //final ArrayList<String> uris = new ArrayList<>();
        final HashMap<String, Object> map = new HashMap<>();

        //map.put("labourUID", "");
        map.put("hostUID", firebaseAuth.getUid());
        map.put("description", event.getDescription());
        map.put("category", event.getCategory());
        map.put("startDate", event.getStartDate());
        map.put("location",event.getLocation());
        map.put("title",event.getTitle());
        map.put("hostImage",event.getHostImage());
        map.put("hostName",event.getHostName());

        //map.put("images", pictures);
        //map.put("labourResponses", new HashMap<String, Long>());
      /*  map.put("addressLine1",services.getAddressLine1());
        map.put("addressLine2",services.getAddressLine2());
        map.put("city",services.getCity());
        map.put("landmark",services.getLandmark());*/
      firebaseFirestore.collection("events").document(event.getEventId()).set(map)
              .addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      Log.d("success","success");
                        onBackPressed();
                  }
              })
              .addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                    Log.d("failure",e.toString());
                  }
              });

    }

    public void selectDateTime(View view) {

        final int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        st = st + year + "/" + monthOfYear + 1 + "/" + dayOfMonth;

                        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        dateTimeEdit.setText(st + " " + hourOfDay + ":" + minute);
                                        st = st + "/" + hourOfDay + "/" + minute;
                                        Log.d("date time", st + "!");
                                        //txtTime.setText(hourOfDay + ":" + minute);
                                    }
                                }, mHour, mMinute, false);

                        timePickerDialog.show();

                        //txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }
}
