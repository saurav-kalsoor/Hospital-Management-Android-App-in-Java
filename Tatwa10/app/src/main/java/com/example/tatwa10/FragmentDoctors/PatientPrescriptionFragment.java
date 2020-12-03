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

import com.example.tatwa10.Adapters.PatientPrescriptionAdapter;
import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.Fragments.PrescriptionInfoFragment;
import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PatientPrescriptionFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientPrescriptionAdapter adapter;
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Prescriptions");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_prescription2);
        View view = inflater.inflate(R.layout.fragment_patient_prescription,container,false);
        DoctorMainActivity.currentFragment = "patient_prescription";
        recyclerView = view.findViewById(R.id.recycler_view_doctor_prescription);

        buildRecyclerView();

        return view;
    }

    private void buildRecyclerView() {
        Query query = collectionReference.whereEqualTo("doctorName", DoctorMainActivity.doctorName);
        FirestoreRecyclerOptions<Prescription> options = new FirestoreRecyclerOptions.Builder<Prescription>()
                .setQuery(query,Prescription.class)
                .build();
        adapter = new PatientPrescriptionAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PatientPrescriptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, DocumentSnapshot documentSnapshot) {
                String id = documentSnapshot.getId();

                Bundle args = new Bundle();
                args.putString("ID",id);
                args.putString("doctor","");
                PrescriptionInfoFragment fragment = new PrescriptionInfoFragment();
                fragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,fragment).addToBackStack(null).commit();
            }
        });
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
