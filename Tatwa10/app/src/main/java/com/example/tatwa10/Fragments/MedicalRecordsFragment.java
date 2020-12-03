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
import com.example.tatwa10.ModelClass.MedicalRecord;
import com.example.tatwa10.Adapters.MedicalRecordAdapter;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MedicalRecordsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddDocument;
    private MedicalRecordAdapter adapter;
    private ProgressDialog dialog;
    private String authNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.navigationView.setCheckedItem(R.id.nav_medical_records);
        MainActivity.currentFragment = "medicalRecords";
        View view = inflater.inflate(R.layout.fragment_medical_records,container,false);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            authNumber = "+918147921339";
        } else {
            authNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();
        dialog.setCancelable(false);

        recyclerView = view.findViewById(R.id.recycler_view_medical_records);
        buttonAddDocument = view.findViewById(R.id.button_add_medical_record);
        buttonAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddMedicalRecordFragment()).addToBackStack(null).commit();
            }
        });

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {


        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("MedicalRecords");
        Query query = collectionReference.whereEqualTo("id",authNumber);
        FirestoreRecyclerOptions<MedicalRecord> options = new FirestoreRecyclerOptions.Builder<MedicalRecord>()
                .setQuery(query,MedicalRecord.class)
                .build();

        adapter = new MedicalRecordAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dialog.cancel();

        adapter.setOnItemClickListener(new MedicalRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, DocumentSnapshot documentSnapshot) {
                MedicalRecord medicalRecord = documentSnapshot.toObject(MedicalRecord.class);
                Bundle args = new Bundle();
                args.putString("title",medicalRecord.getTitle());
                MedicalRecordInfoFragment fragment = new MedicalRecordInfoFragment();
                fragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();

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


