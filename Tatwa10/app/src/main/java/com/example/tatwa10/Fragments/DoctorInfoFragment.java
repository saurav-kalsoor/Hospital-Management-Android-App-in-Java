package com.example.tatwa10.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.MainActivity;
import com.example.tatwa10.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorInfoFragment extends Fragment {

    private CircleImageView imageViewDoctor;
    private TextView textViewName;
    private TextView textViewField;
    private TextView textViewQualification;
    private Button buttonBookAppointment;
    private String doctorName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.navigationView.setCheckedItem(R.id.nav_doctors);
        MainActivity.currentFragment = "doctorInfo";
        View view = inflater.inflate(R.layout.fragment_doctor_info,container,false);

        textViewName = view.findViewById(R.id.text_view_name_doctor_info);
        textViewField = view.findViewById(R.id.text_view_field_doctor_info);
        textViewQualification = view.findViewById(R.id.text_view_qualification_doctor_info);
        imageViewDoctor = view.findViewById(R.id.image_doctor_info);
        buttonBookAppointment = view.findViewById(R.id.button_doctor_book_appointment);


        if(getArguments()!=null) {
            doctorName = getArguments().getString(FindDoctorsFragment.DOCTOR_NAME);
            textViewName.setText(getArguments().getString(FindDoctorsFragment.DOCTOR_NAME));
            textViewField.setText(getArguments().getString(FindDoctorsFragment.DOCTOR_FIELD));
            textViewQualification.setText(getArguments().getString(FindDoctorsFragment.DOCTOR_QUALIFICATION));
            imageViewDoctor.setImageResource(getArguments().getInt(FindDoctorsFragment.DOCTOR_IMAGE,0));
        }

        buttonBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment();
            }
        });

        return view;
    }

    private void bookAppointment() {
        BookAppointmentFragment fragment = new BookAppointmentFragment();
        Bundle args = new Bundle();
        args.putString("docName",doctorName);
        fragment.setArguments(args);
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}
