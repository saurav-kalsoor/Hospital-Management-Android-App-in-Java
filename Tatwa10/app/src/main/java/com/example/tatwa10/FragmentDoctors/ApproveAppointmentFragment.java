package com.example.tatwa10.FragmentDoctors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.Adapters.ApproveAppointmentAdapter;
import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ApproveAppointmentFragment extends Fragment {

    private RecyclerView recyclerViewAppointmentList;
    private ApproveAppointmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_approve_appointment2);
        View view = inflater.inflate(R.layout.fragment_approve_appointment,container,false);
        DoctorMainActivity.currentFragment = "approve_appointment";

        recyclerViewAppointmentList = view.findViewById(R.id.recycler_view_approve_appointment);
        buildRecyclerView();

        return view;
    }

    private void buildRecyclerView() {
        final CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Appointments");
        Query query = collectionReference.whereEqualTo("appointmentAccepted",false).whereEqualTo("appointmentDone",false).whereEqualTo("doctorName", DoctorMainActivity.doctorName);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query,Appointment.class)
                .build();

        adapter = new ApproveAppointmentAdapter(options, new ApproveAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onAcceptClick(int position, final DocumentSnapshot documentSnapshot) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Accept")
                        .setMessage("Are you sure you want to accept this appointment?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = documentSnapshot.getId();
                                Appointment appointment = documentSnapshot.toObject(Appointment.class);
                                collectionReference.document(id).update("appointmentAccepted",true);
                                Toast.makeText(getContext(),"Appointment Accepted",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }

            @Override
            public void onRejectClick(int position, final DocumentSnapshot documentSnapshot) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Reject")
                        .setMessage("Are you sure you want to reject this appointment?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = documentSnapshot.getId();
                                collectionReference.document(id).delete();
                                Toast.makeText(getContext(),"Appointment Rejected",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        recyclerViewAppointmentList.setHasFixedSize(true);
        recyclerViewAppointmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAppointmentList.setAdapter(adapter);
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
