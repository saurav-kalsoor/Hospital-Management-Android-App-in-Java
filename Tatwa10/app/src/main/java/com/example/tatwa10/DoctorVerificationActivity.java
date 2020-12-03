package com.example.tatwa10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tatwa10.ModelClass.Doctor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DoctorVerificationActivity extends AppCompatActivity {

    private EditText editTextPassword;
    private Button buttonLogin;
    private Spinner spinnerDoctor;
    private Doctor doctor;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_verification);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Signing In... Please wait");

        editTextPassword = findViewById(R.id.edit_text_doctor_password);
        buttonLogin = findViewById(R.id.button_doctor_login);
        spinnerDoctor = findViewById(R.id.spinner_doctors_login);

        String[] names = getResources().getStringArray(R.array.doctors_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDoctor();
            }
        });
    }

    private synchronized void loginDoctor() {

        String name = spinnerDoctor.getSelectedItem().toString();
        String password = editTextPassword.getText().toString().trim() + "";

        if (TextUtils.isEmpty(password) || password.length() < 8) {
            Toast.makeText(DoctorVerificationActivity.this, "Please enter a Valid Password", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
            getReference(name, password);
        }
    }

    private void getReference(final String name, final String password) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Doctors");
        collectionReference.whereEqualTo("name", name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        doctor = snapshot.toObject(Doctor.class);
                        break;
                    }
                    String checkPassword = doctor.getPassword();
                    if (checkPassword.equals(password)) {
                        dialog.cancel();
                        Toast.makeText(DoctorVerificationActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DoctorVerificationActivity.this,DoctorMainActivity.class);
                        intent.putExtra("name",name);
                        startActivity(intent);

                    } else {
                        dialog.cancel();
                        Toast.makeText(DoctorVerificationActivity.this, "Incorrect Password, Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.cancel();
                Toast.makeText(DoctorVerificationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        startActivity(new Intent(DoctorVerificationActivity.this,DoctorMainActivity.class));
//    }
}
