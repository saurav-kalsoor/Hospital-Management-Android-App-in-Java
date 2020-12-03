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

public class PendingAppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, PendingAppointmentAdapter.PendingHolder> {

    private OnItemClickListener listener;

    public PendingAppointmentAdapter(@NonNull FirestoreRecyclerOptions<Appointment> options,OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PendingHolder holder, int i, @NonNull Appointment appointment) {
        holder.textViewPatientName.setText(appointment.getName());
        holder.textViewPatientContact.setText(appointment.getId());
        holder.textViewAppointmentDate.setText(appointment.getAppointmentDate());
        holder.textViewAppointmentTime.setText(appointment.getAppointmentTime());
    }

    @NonNull
    @Override
    public PendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_appointment_item,parent,false);
        return new PendingHolder(view);
    }

    class PendingHolder extends RecyclerView.ViewHolder {

        private TextView textViewPatientName;
        private TextView textViewPatientContact;
        private TextView textViewAppointmentDate;
        private TextView textViewAppointmentTime;
        private ImageView imageViewCall;
        private ImageView imageViewMessage;

        public PendingHolder(@NonNull View itemView) {
            super(itemView);

            textViewPatientName = itemView.findViewById(R.id.text_view_item_pending_appointment_patient_name);
            textViewPatientContact = itemView.findViewById(R.id.text_view_item_pending_appointment_patient_contact);
            textViewAppointmentDate = itemView.findViewById(R.id.text_view_item_pending_appointment_date);
            textViewAppointmentTime = itemView.findViewById(R.id.text_view_item_pending_appointment_time);
            imageViewCall = itemView.findViewById(R.id.image_view_call_patient);
            imageViewMessage = itemView.findViewById(R.id.image_view_message_patient);

            imageViewCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String number = getSnapshots().getSnapshot(position).toObject(Appointment.class).getId();
                    if (number != null && listener != null) {
                        listener.onCallClick(number);
                    }
                }
            });

            imageViewMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String number = getSnapshots().getSnapshot(position).toObject(Appointment.class).getId();
                    if(number != null && listener != null) {
                        listener.onMessageClick(number);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onCallClick(String number);
        void onMessageClick(String number);
    }
}
