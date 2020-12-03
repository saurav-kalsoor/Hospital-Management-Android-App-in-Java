package com.example.tatwa10.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tatwa10.ModelClass.Doctor;
import com.example.tatwa10.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;
    private Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DoctorsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,parent,false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor currentDoc = doctorList.get(position);
        holder.imageViewDoctor.setImageResource(currentDoc.getImageUri());
        holder.textViewDoctorName.setText(currentDoc.getName());
        holder.textViewDoctorSpecification.setText(currentDoc.getSpecification());



    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @Override
    public int getItemCount() {
        if(doctorList!=null){
            return doctorList.size();
        }
        return 0;

    }

    class DoctorViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageViewDoctor;
        TextView textViewDoctorName;
        TextView textViewDoctorSpecification;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewDoctor = itemView.findViewById(R.id.image_doctor_item);
            textViewDoctorName = itemView.findViewById(R.id.text_view_doctor_name_item);
            textViewDoctorSpecification = itemView.findViewById(R.id.text_view_doctor_specification_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(doctorList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Doctor doctor);
    }
}
