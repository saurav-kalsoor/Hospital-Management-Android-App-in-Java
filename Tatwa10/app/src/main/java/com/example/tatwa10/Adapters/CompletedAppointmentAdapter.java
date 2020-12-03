package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CompletedAppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, CompletedAppointmentAdapter.CompletedHolder> {

    public CompletedAppointmentAdapter(@NonNull FirestoreRecyclerOptions<Appointment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CompletedHolder holder, int i, @NonNull Appointment appointment) {
        holder.textViewPatientName.setText(appointment.getName());
        holder.textViewPatientContact.setText(appointment.getId());
        holder.textViewAppointmentDate.setText(appointment.getAppointmentDate());
        holder.textViewAppointmentTime.setText(appointment.getAppointmentTime());
    }

    @NonNull
    @Override
    public CompletedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_appointment_item,parent,false);
        return new CompletedHolder(view);
    }

    class CompletedHolder extends RecyclerView.ViewHolder {

        private TextView textViewPatientName;
        private TextView textViewPatientContact;
        private TextView textViewAppointmentDate;
        private TextView textViewAppointmentTime;


        public CompletedHolder(@NonNull View itemView) {
            super(itemView);

            textViewPatientName = itemView.findViewById(R.id.text_view_item_completed_appointment_patient_name);
            textViewPatientContact = itemView.findViewById(R.id.text_view_item_completed_patient_contact);
            textViewAppointmentDate = itemView.findViewById(R.id.text_view_item_completed_appointment_date);
            textViewAppointmentTime = itemView.findViewById(R.id.text_view_item_completed_appointment_time);
        }
    }

}
