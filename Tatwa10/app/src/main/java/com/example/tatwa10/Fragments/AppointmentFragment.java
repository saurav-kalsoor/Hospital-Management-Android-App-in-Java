package com.example.tatwa10.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.Adapters.AppointmentAdapter;
import com.example.tatwa10.MainActivity;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddAppointment;
    private CollectionReference collectionReference;
    private AppointmentAdapter adapter;
    private ProgressDialog dialog;
    private String authNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();
        dialog.setCancelable(false);
        MainActivity.navigationView.setCheckedItem(R.id.nav_appointment);
        MainActivity.currentFragment = "appointment";
        View view = inflater.inflate(R.layout.fragment_appointment,container,false);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            authNumber = "+918147921339";
        } else {
            authNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }


        recyclerView = view.findViewById(R.id.recycler_view_appointment_list);
        buttonAddAppointment = view.findViewById(R.id.button_add_appointment);
        buttonAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new BookAppointmentFragment()).addToBackStack(null).commit();
            }
        });

        collectionReference = FirebaseFirestore.getInstance().collection("Appointments");
        setUpRecyclerView();


        return view;
    }

    private synchronized void setUpRecyclerView() {
        Query query = collectionReference.whereEqualTo("id",authNumber);
        //Query query = collectionReference.orderBy("appointmentDate", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query,Appointment.class)
                .build();

        adapter = new AppointmentAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        dialog.cancel();

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
