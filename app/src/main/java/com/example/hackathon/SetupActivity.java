package com.example.hackathon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class SetupActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;
    private Button submit;
    private ImageView photo;
    private Uri mainImageURI = null;
    private ProgressBar progressBar;
    private EditText name, phone, dob, skill, workExperience;
    private TextInputLayout date;
    private EditText a1, a2, a3, state, year;
    private Boolean server = true, isChanged = false;
    private FirebaseAuth firebaseAuth;
    private Bitmap compressedImageFile;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private String userId, type;
    private TextInputLayout skillTil, workTil;
    private Toolbar toolbar;
    private SessionManager session;
    private User user;

    ArrayList<String> selectedStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        toolbar = findViewById(R.id.setup_tb);
        toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        user = new User();

        session = new SessionManager(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        /*viewPager = findViewById(R.id.setup_vp);
        tabs = findViewById(R.id.setup_tl);*/

        submit = findViewById(R.id.setup2_btn_submit);
        photo = findViewById(R.id.setup_iv_photo);
        name = findViewById(R.id.setup_details_et_name);
        phone = findViewById(R.id.setup_details_et_phone);
        year = findViewById(R.id.setup_address_et_year);
        progressBar = findViewById(R.id.setup_pb);

        progressBar.setVisibility(View.GONE);

        /*if (type.equals("customer")) {
            skillTil.setVisibility(View.GONE);
            workTil.setVisibility(View.GONE);
            skill.setVisibility(View.GONE);
            workExperience.setVisibility(View.GONE);
        }*/

      /*  date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });

        skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSkill(v);
            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToFirebase();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(SetupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        //Toast.makeText(SetupActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(SetupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        BringImagePicker();

                    }

                } else {
                    BringImagePicker();
                }
            }
        });
    }

    private void sendToFirebase() {

        progressBar.setVisibility(View.VISIBLE);
        if (isChanged) {

            userId = firebaseAuth.getCurrentUser().getUid();

            File newImageFile = new File(mainImageURI.getPath());
            try {

                compressedImageFile = new Compressor(SetupActivity.this)
                        .setMaxHeight(160)
                        .setMaxWidth(120)
                        .setQuality(50)
                        .compressToBitmap(newImageFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] thumbData = baos.toByteArray();

            final UploadTask image_path = storageReference.child("profile_images").child(userId + ".jpg").putBytes(thumbData);

            image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()) {

                        storageReference.child("profile_images").child(userId + ".jpg").getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        storeFirestore(uri);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SetupActivity.this, "(IMAGE Error uri) : " + e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });

                    } else {

                        String error = task.getException().getMessage();
                        Toast.makeText(SetupActivity.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }
            });

        } else {
            storeFirestore(null);
        }
    }

    private void storeFirestore(Uri uri) {

        Map<String, Object> userMap = new HashMap<>();
        String image = "null";
        userMap.put("image", "null");
        if (uri != null) {
            userMap.put("image", uri.toString());
            image = uri.toString();
            user.setImage(image);
        }

        userMap.put("name", name.getText().toString());
        user.setName(name.getText().toString());
        userMap.put("phone", Long.valueOf(phone.getText().toString()));
        user.setPhone(Long.valueOf(phone.getText().toString()));
        userMap.put("year", year.getText().toString());
        user.setYear(year.getText().toString());
        /*userMap.put("state", state.getText().toString());
        userMap.put("addressLine1", a1.getText().toString());
        userMap.put("addressLine2", a2.getText().toString());
        userMap.put("addressLine3", a3.getText().toString());
        userMap.put("dob", dob.getText().toString());
        userMap.put("wallet", 0L);*/

       /* if (type.equals("labourer")) {
            userMap.put("skill", selectedStrings);
            userMap.put("workExperience", Long.valueOf(workExperience.getText().toString()));
            *//*session.createProfileLabourer(name.getText().toString(),"null",dob.getText().toString(),city.getText().toString(),state.getText().toString(),
                    Long.valueOf(phone.getText().toString()),a1.getText().toString(),a2.getText().toString(),a3.getText().toString(),selectedStrings
            ,Long.valueOf(workExperience.getText().toString()));*//*
        } else {
            customerFinal = new CustomerFinal(userId, name.getText().toString(), image, dob.getText().toString(), city.getText().toString(),
                    state.getText().toString(), a1.getText().toString(), a2.getText().toString(), a3.getText().toString(),
                    Long.valueOf(phone.getText().toString()), 0L, new ArrayList<String>());
            *//*session.createProfileCustomer(name.getText().toString(),"null",dob.getText().toString(),city.getText().toString(),state.getText().toString(),
                    Long.valueOf(phone.getText().toString()),a1.getText().toString(),a2.getText().toString(),a3.getText().toString());*//*
        }
        ArrayList<String> h = new ArrayList<>();

        userMap.put("services", h);*/

        firebaseFirestore.collection("informal").document(userId).set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SetupActivity.this, "The user Settings are updated.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(SetupActivity.this, InformalHomeActivity.class);

                        intent.putExtra("informal", user);
                        startActivity(intent);
                        finish();

                        /*if (type.equals("customer")) {
                            session.saveCustomer(customerFinal);
                            Intent intent = new Intent(SetupActivity.this, CustomerHomeActivity.class);

                            intent.putExtra("customer", customerFinal);
                            startActivity(intent);
                        } else {
                            session.saveLabourer(labourerFinal);
                            Intent intent = new Intent(SetupActivity.this, LabourerHomeActivity.class);
                            //intent.putExtra("labourer",labourerFinal);
                            startActivity(intent);
                        }
                        finish();*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //String error = task.getException().getMessage();
                        Toast.makeText(SetupActivity.this, "(FIRESTORE Error) : " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void BringImagePicker() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(120, 160)
                .start(SetupActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI = result.getUri();
                photo.setImageURI(mainImageURI);

                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

}
