package com.example.tatwa10.FragmentDoctors;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.PendingAppointmentAdapter;
import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PendingAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private PendingAppointmentAdapter adapter;
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Appointments");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_pending_appointment2);
        View view = inflater.inflate(R.layout.fragment_pending_appointments,container,false);
        recyclerView = view.findViewById(R.id.recycler_view_pending_appointment);
        DoctorMainActivity.currentFragment = "pending_appointment";

        buildRecyclerView();

        return view;
    }

    private void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        Query query = collectionReference.whereEqualTo("doctorName",DoctorMainActivity.doctorName).whereEqualTo("appointmentDone",false).whereEqualTo("appointmentAccepted",true);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query,Appointment.class)
                .build();

        adapter = new PendingAppointmentAdapter(options, new PendingAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onCallClick(String number) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                startActivity(callIntent);
            }

            @Override
            public void onMessageClick(String number) {
                Intent messageIntent = new Intent(Intent.ACTION_VIEW);
                messageIntent.setData(Uri.parse("sms:"+number));
                startActivity(messageIntent);
            }
        });
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
