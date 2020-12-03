package com.example.tatwa10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StartingActivity extends AppCompatActivity {

    private Button buttonDoctor;
    private Button buttonPatient;
    private Button buttonContinue;
    private boolean isDoctor;
    public static final String SHARED_PREFERENCES = "shared_prefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        isDoctor = false;
        buttonDoctor = findViewById(R.id.button_start_doctor);
        buttonPatient = findViewById(R.id.button_start_patient);
        buttonContinue = findViewById(R.id.button_start_continue);

        buttonDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorClicked();
            }
        });

        buttonPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientClicked();
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueClicked();
            }
        });




    }

    private void continueClicked() {
        if(isDoctor) {
            startActivity(new Intent(StartingActivity.this,DoctorVerificationActivity.class));
        } else {
            startActivity(new Intent(StartingActivity.this,PhoneVerificationActivity.class));
        }
    }

    private void patientClicked() {
        isDoctor = false;
        buttonPatient.setBackground(getResources().getDrawable(R.drawable.custom_button_border_2));
        buttonDoctor.setBackground(getResources().getDrawable(R.drawable.custom_button_border_3));
    }

    private void doctorClicked() {
        isDoctor = true;
        buttonPatient.setBackground(getResources().getDrawable(R.drawable.custom_button_border_3));
        buttonDoctor.setBackground(getResources().getDrawable(R.drawable.custom_button_border_2));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(StartingActivity.this, MainActivity.class));
        }
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        String name = sharedPreferences.getString("name","no name");
        if (!name.equals("no name")){
            Intent intent = new Intent(StartingActivity.this,DoctorMainActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }

    }
}
