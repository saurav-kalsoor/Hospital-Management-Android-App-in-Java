package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class PatientPrescriptionAdapter extends FirestoreRecyclerAdapter<Prescription, PatientPrescriptionAdapter.PatientHolder> {

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PatientPrescriptionAdapter(@NonNull FirestoreRecyclerOptions<Prescription> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PatientHolder holder, int i, @NonNull Prescription prescription) {
        holder.textViewPatientName.setText(prescription.getPatientName());
        holder.textViewMedicineName.setText(prescription.getMedicineName());
        holder.textViewDateStart.setText(prescription.getDateStart());
        holder.textViewDateEnd.setText(prescription.getDateEnd());
        holder.textViewDuration.setText(String.valueOf(prescription.getDuration()));

        if (prescription.isBreakfast()) {
            holder.buttonBreakfast.setVisibility(View.VISIBLE);
        }
        if (prescription.isLunch()) {
            holder.buttonLunch.setVisibility(View.VISIBLE);
        }
        if (prescription.isDinner()) {
            holder.buttonDinner.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_prescription_item,parent,false);
        return new PatientHolder(view);
    }

    class PatientHolder extends RecyclerView.ViewHolder {

        private TextView textViewPatientName;
        private TextView textViewMedicineName;
        private Button buttonBreakfast;
        private Button buttonLunch;
        private Button buttonDinner;
        private TextView textViewDateStart;
        private TextView textViewDateEnd;
        private TextView textViewDuration;


        public PatientHolder(@NonNull View itemView) {
            super(itemView);

            textViewPatientName = itemView.findViewById(R.id.text_view_doctor_prescription_patient_name);
            textViewMedicineName = itemView.findViewById(R.id.text_view_doctor_prescription_medicine_name);
            buttonBreakfast = itemView.findViewById(R.id.button_breakfast_doctor_prescription);
            buttonLunch = itemView.findViewById(R.id.button_lunch_doctor_prescription);
            buttonDinner = itemView.findViewById(R.id.button_dinner_doctor_prescription);
            textViewDateStart = itemView.findViewById(R.id.text_view_date_start_doctor_prescription);
            textViewDateEnd = itemView.findViewById(R.id.text_view_date_end_doctor_prescription);
            textViewDuration = itemView.findViewById(R.id.text_view_item_medicine_duration_doctor_prescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position,getSnapshots().getSnapshot(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, DocumentSnapshot documentSnapshot);
    }
}
