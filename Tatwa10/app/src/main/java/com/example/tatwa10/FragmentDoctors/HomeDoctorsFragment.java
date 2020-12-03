package com.example.tatwa10.FragmentDoctors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.MainActivity;
import com.example.tatwa10.R;

public class HomeDoctorsFragment extends Fragment {

    private TextView textViewName;
    private Button buttonApproveAppointment;
    private Button buttonPendingAppointment;
    private Button buttonCompletedAppointment;
    private Button buttonPatientPrescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_doctors,container,false);
        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_home2);
        DoctorMainActivity.currentFragment = "home";

        textViewName = view.findViewById(R.id.text_view_home_name);
        textViewName.setText(DoctorMainActivity.doctorName);

        buttonApproveAppointment = view.findViewById(R.id.button_approve_appointments);
        buttonPendingAppointment = view.findViewById(R.id.button_pending_appointments);
        buttonCompletedAppointment = view.findViewById(R.id.button_completed_appointments);
        buttonPatientPrescription = view.findViewById(R.id.button_your_patients);

        buttonApproveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,new ApproveAppointmentFragment()).addToBackStack(null).commit();
            }
        });

        buttonPendingAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,new PendingAppointmentFragment()).addToBackStack(null).commit();
            }
        });

        buttonCompletedAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,new CompletedAppointmentFragment()).addToBackStack(null).commit();
            }
        });

        buttonPatientPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,new PatientPrescriptionFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
