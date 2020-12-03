package com.example.tatwa10.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Doctor;
import com.example.tatwa10.DoctorDbHelper;
import com.example.tatwa10.Adapters.DoctorsAdapter;
import com.example.tatwa10.MainActivity;
import com.example.tatwa10.R;


public class FindDoctorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DoctorsAdapter adapter;
    private DoctorDbHelper db;

    public static final String DOCTOR_NAME = "doctor_name";
    public static final String DOCTOR_FIELD = "doctor_field";
    public static final String DOCTOR_QUALIFICATION = "doctor_qualification";
    public static final String DOCTOR_IMAGE = "doctor_image";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.navigationView.setCheckedItem(R.id.nav_doctors);
        MainActivity.currentFragment = "findDoctors";
        View view = inflater.inflate(R.layout.fragment_find_doctors,container,false);


        db = new DoctorDbHelper(getContext());
        recyclerView = view.findViewById(R.id.recycler_view_doctors_list);
        recyclerView.setHasFixedSize(true);

        adapter = new DoctorsAdapter(getContext());


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setDoctorList(db.getDoctorsList());

        adapter.setOnItemClickListener(new DoctorsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Doctor doctor) {
                Bundle args = new Bundle();
                args.putString(DOCTOR_NAME,doctor.getName());
                args.putString(DOCTOR_FIELD,doctor.getSpecification());
                args.putString(DOCTOR_QUALIFICATION,doctor.getQualifications());
                args.putInt(DOCTOR_IMAGE,doctor.getImageUri());
                DoctorInfoFragment fragment = new DoctorInfoFragment();
                fragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
            }
        });



        return view;

    }




}
