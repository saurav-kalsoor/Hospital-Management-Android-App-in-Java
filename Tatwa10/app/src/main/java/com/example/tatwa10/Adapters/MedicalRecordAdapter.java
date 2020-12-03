package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.MedicalRecord;
import com.example.tatwa10.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class MedicalRecordAdapter extends FirestoreRecyclerAdapter<MedicalRecord, MedicalRecordAdapter.MedicalViewHolder> {

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MedicalRecordAdapter(@NonNull FirestoreRecyclerOptions<MedicalRecord> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MedicalViewHolder holder, int i, @NonNull MedicalRecord medicalRecord) {
        holder.textViewTitle.setText(medicalRecord.getTitle());
    }

    @NonNull
    @Override
    public MedicalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_record_item,parent,false);
        return new MedicalViewHolder(view);
    }

    class MedicalViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;

        public MedicalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_medical_records_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
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
