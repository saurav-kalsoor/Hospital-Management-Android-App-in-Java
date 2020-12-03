package com.example.tatwa10.FragmentDoctors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.CompletedAppointmentAdapter;
import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CompletedAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private CompletedAppointmentAdapter adapter;
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Appointments");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_completed_appointment2);
        View view = inflater.inflate(R.layout.fragment_completed_appointment,container,false);
        DoctorMainActivity.currentFragment = "completed_appointment";
        recyclerView = view.findViewById(R.id.recycler_view_completed_appointment);

        buildRecyclerView();


        return view;
    }

    private void buildRecyclerView() {
        Query query = collectionReference.whereEqualTo("doctorName", DoctorMainActivity.doctorName).whereEqualTo("appointmentDone",true);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query,Appointment.class)
                .build();
        adapter = new CompletedAppointmentAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
