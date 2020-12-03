package com.example.tatwa10.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tatwa10.MainActivity;
import com.example.tatwa10.R;

public class HomeFragment extends Fragment {

    private Button buttonFindDoctors;
    private Button buttonBookAppointment;
    private Button buttonPrescriptions;
    private Button buttonMedicalRecords;
    private Button buttonCallAmbulance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.navigationView.setCheckedItem(R.id.nav_home);
        MainActivity.currentFragment = "home";
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        buttonFindDoctors = view.findViewById(R.id.button_find_doctors);
        buttonBookAppointment = view.findViewById(R.id.button_book_appointment);
        buttonPrescriptions = view.findViewById(R.id.button_view_prescriptions);
        buttonMedicalRecords = view.findViewById(R.id.button_medical_records);
        buttonCallAmbulance = view.findViewById(R.id.button_emergency);

        buttonFindDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new FindDoctorsFragment()).addToBackStack(null).commit();
            }
        });


        buttonBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new BookAppointmentFragment()).addToBackStack(null).commit();
            }
        });

        buttonPrescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new PrescriptionFragment()).addToBackStack(null).commit();
            }
        });

        buttonMedicalRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new MedicalRecordsFragment()).addToBackStack(null).commit();
            }
        });

        buttonCallAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new CallAmbulanceFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }

}
