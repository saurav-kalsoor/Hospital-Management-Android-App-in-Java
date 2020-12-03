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

import com.example.tatwa10.MainActivity;
import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.Adapters.PrescriptionAdapter;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PrescriptionFragment extends Fragment {

    private RecyclerView recyclerView;
    private CollectionReference collectionReference;
    private FloatingActionButton buttonAdd;
    private PrescriptionAdapter adapter;
    private ProgressDialog progressDialog;
    private String authNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.navigationView.setCheckedItem(R.id.nav_prescription);
        MainActivity.currentFragment = "prescription";
        View view = inflater.inflate(R.layout.fragment_prescription,container,false);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            authNumber = "+918147921339";
        } else {
            authNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        recyclerView = view.findViewById(R.id.recycler_view_prescription_list);
        buttonAdd = view.findViewById(R.id.button_add_prescription);
        collectionReference = FirebaseFirestore.getInstance().collection("Prescriptions");

        setupRecyclerView();


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddPrescriptionFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }


    private void setupRecyclerView() {
        Query query = collectionReference.whereEqualTo("id",authNumber);
        FirestoreRecyclerOptions<Prescription> options = new FirestoreRecyclerOptions.Builder<Prescription>()
                .setQuery(query,Prescription.class)
                .build();

        adapter = new PrescriptionAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



        adapter.setOnItemClickListener(new PrescriptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();

                Bundle args = new Bundle();
                args.putString("ID",id);
                PrescriptionInfoFragment fragment = new PrescriptionInfoFragment();
                fragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
            }
        });
        progressDialog.cancel();
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
