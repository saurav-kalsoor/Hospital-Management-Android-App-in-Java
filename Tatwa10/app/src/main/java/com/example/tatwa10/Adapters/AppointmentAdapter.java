package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, AppointmentAdapter.AppointmentHolder> {

    public AppointmentAdapter(@NonNull FirestoreRecyclerOptions<Appointment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AppointmentHolder holder, int i, @NonNull Appointment appointment) {
        holder.textViewDoctorName.setText(appointment.getDoctorName());
        holder.textViewAppointmentDate.setText(appointment.getAppointmentDate());
        holder.textViewAppointmentTime.setText(appointment.getAppointmentTime());

        if (appointment.isAppointmentDone()) {
            holder.imageViewAppointmentCompleted.setVisibility(View.VISIBLE);
        }

        if (appointment.isAppointmentAccepted()) {
            holder.textViewAppointmentStatus.setText("Appointment accepted by doctor");
        } else {
            holder.textViewAppointmentStatus.setText("Approval Pending, request sent to doctor");
        }


    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item, parent, false);
        return new AppointmentHolder(view);

    }

    class AppointmentHolder extends RecyclerView.ViewHolder {

        private TextView textViewDoctorName;
        private TextView textViewAppointmentDate;
        private TextView textViewAppointmentTime;
        private TextView textViewAppointmentStatus;
        private ImageView imageViewAppointmentCompleted;


        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            textViewDoctorName = itemView.findViewById(R.id.text_view_item_appointment_doctor_name);
            textViewAppointmentDate = itemView.findViewById(R.id.text_view_item_appointment_date);
            textViewAppointmentTime = itemView.findViewById(R.id.text_view_item_appointment_time);
            textViewAppointmentStatus = itemView.findViewById(R.id.text_view_item_appointment_status);
            imageViewAppointmentCompleted = itemView.findViewById(R.id.image_view_appointment_completed);
        }
    }

}
