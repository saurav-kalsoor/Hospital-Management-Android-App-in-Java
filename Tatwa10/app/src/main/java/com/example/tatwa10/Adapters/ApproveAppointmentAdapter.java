package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ApproveAppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, ApproveAppointmentAdapter.AppointmentHolder> {

    private OnItemClickListener listener;

    public ApproveAppointmentAdapter(@NonNull FirestoreRecyclerOptions<Appointment> options,OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull AppointmentHolder holder, int i, @NonNull Appointment appointment) {



        holder.textViewPatientName.setText(appointment.getName());
        holder.textViewAppointmentDate.setText(appointment.getAppointmentDate());
        holder.textViewAppointmentTime.setText(appointment.getAppointmentTime());
        holder.textViewContactNo.setText(appointment.getId());
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approve_appointment_item,parent,false);
        return new AppointmentHolder(view);
    }

    class AppointmentHolder extends RecyclerView.ViewHolder {

        private TextView textViewPatientName;
        private TextView textViewAppointmentDate;
        private TextView textViewAppointmentTime;
        private TextView textViewContactNo;
        private Button buttonAccept;
        private Button buttonReject;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);

            textViewPatientName = itemView.findViewById(R.id.text_view_item_approve_appointment_patient_name);
            textViewAppointmentDate = itemView.findViewById(R.id.text_view_item_approve_appointment_date);
            textViewAppointmentTime = itemView.findViewById(R.id.text_view_item_approve_appointment_time);
            textViewContactNo = itemView.findViewById(R.id.text_view_item_approve_patient_contact);
            buttonAccept = itemView.findViewById(R.id.button_item_accept_appointment);
            buttonReject = itemView.findViewById(R.id.button_item_reject_appointment);

            buttonAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(position);
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onAcceptClick(position,documentSnapshot);
                    }
                }
            });

            buttonReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(position);
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onRejectClick(position,documentSnapshot);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onAcceptClick(int position,DocumentSnapshot documentSnapshot);
        void onRejectClick(int position,DocumentSnapshot documentSnapshot);
    }
}
