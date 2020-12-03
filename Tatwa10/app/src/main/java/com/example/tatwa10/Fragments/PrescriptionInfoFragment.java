package com.example.tatwa10.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.FragmentDoctors.PatientPrescriptionFragment;
import com.example.tatwa10.MainActivity;
import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PrescriptionInfoFragment extends Fragment {

    private Spinner spinnerDoctorsList;
    private EditText editTextMedicineName;
    private CheckBox checkBoxBreakfast;
    private CheckBox checkBoxLunch;
    private CheckBox checkBoxDinner;
    private NumberPicker numberPickerDuration;
    private ImageView imageViewDateStart;
    private TextView textViewDateStart;
    private TextView textViewDateEnd;
    private Calendar calendarDateStart;
    private Calendar calendarDateEnd;
    private String id;
    private Prescription oldPrescription;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_add_prescription, container, false);
        MainActivity.currentFragment = "prescriptionInfo";
        spinnerDoctorsList = view.findViewById(R.id.spinner_doctors_list);
        editTextMedicineName = view.findViewById(R.id.edit_text_medicine_name);
        checkBoxBreakfast = view.findViewById(R.id.checkbox_breakfast);
        checkBoxLunch = view.findViewById(R.id.checkbox_lunch);
        checkBoxDinner = view.findViewById(R.id.checkbox_dinner);
        numberPickerDuration = view.findViewById(R.id.number_picker_duration);
        imageViewDateStart = view.findViewById(R.id.image_view_date_start);
        textViewDateStart = view.findViewById(R.id.text_view_add_date_start);
        textViewDateEnd = view.findViewById(R.id.text_view_add_date_end);

        numberPickerDuration.setMinValue(1);
        numberPickerDuration.setMaxValue(50);

        calendarDateStart = Calendar.getInstance();


        String[] names = getContext().getResources().getStringArray(R.array.doctors_name);
        ArrayAdapter<String> adapterNames = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, names);
        adapterNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctorsList.setAdapter(adapterNames);

        setUp(adapterNames);


        imageViewDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        numberPickerDuration.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calendarDateEnd = calendarDateStart;
                calendarDateEnd.add(Calendar.DATE,newVal-oldVal);
                DateFormat dateFormat1 = new SimpleDateFormat("E ,dd MMM yyyy");
                textViewDateEnd.setText(dateFormat1.format(calendarDateEnd.getTime()));
            }
        });


        return view;
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarDateStart.set(Calendar.YEAR,year);
                calendarDateStart.set(Calendar.MONTH,month);
                calendarDateStart.set(Calendar.DATE,dayOfMonth);

                DateFormat dateFormatStart = new SimpleDateFormat("E ,dd MMM yyyy");


                textViewDateStart.setText(dateFormatStart.format(calendarDateStart.getTime()));

                calendarDateEnd = calendarDateStart;
                calendarDateEnd.add(Calendar.DATE,numberPickerDuration.getValue()-1);

                DateFormat dateFormatEnd = new SimpleDateFormat("E ,dd MMM yyyy");

                textViewDateEnd.setText(dateFormatEnd.format(calendarDateEnd.getTime()));
            }
        });

        date.show(getParentFragmentManager(), "Date Picker");
    }



    private synchronized void  setUp(final ArrayAdapter<String> adapter) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        id = getArguments().getString("ID");
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Prescriptions").document(id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    oldPrescription = documentSnapshot.toObject(Prescription.class);
                }
                int spinnerPosition = adapter.getPosition(oldPrescription.getDoctorName());
                spinnerDoctorsList.setSelection(spinnerPosition);
                editTextMedicineName.setText(oldPrescription.getMedicineName());
                checkBoxBreakfast.setChecked(oldPrescription.isBreakfast());
                checkBoxLunch.setChecked(oldPrescription.isLunch());
                checkBoxDinner.setChecked(oldPrescription.isDinner());
                textViewDateStart.setText(oldPrescription.getDateStart());
                textViewDateEnd.setText(oldPrescription.getDateEnd());
                numberPickerDuration.setValue(oldPrescription.getDuration());
                progressDialog.cancel();
            }
        });



    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_prescription_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.icon_save_prescription) {
            savePrescription();
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePrescription() {
        String doctorName = spinnerDoctorsList.getSelectedItem().toString();
        String medicineName = editTextMedicineName.getText().toString().trim() + "";
        boolean breakfast = checkBoxBreakfast.isChecked();
        boolean lunch = checkBoxLunch.isChecked();
        boolean dinner = checkBoxDinner.isChecked();
        int duration = numberPickerDuration.getValue();
        String dateStart = textViewDateStart.getText().toString() + "";
        String dateEnd = textViewDateEnd.getText().toString() + "";

        if(TextUtils.isEmpty(medicineName)) {
            Toast.makeText(getContext(),"Please Add Medicine",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!breakfast && !lunch && !dinner){
            Toast.makeText(getContext(),"Please Select Time",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(dateStart)) {
            Toast.makeText(getContext(),"Please Choose Date",Toast.LENGTH_SHORT).show();
            return;
        }



        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Prescriptions").document(id);
        documentReference.update("medicineName",medicineName);
        documentReference.update("breakfast",breakfast);
        documentReference.update("lunch",lunch);
        documentReference.update("dinner",dinner);
        documentReference.update("dateStart",dateStart);
        documentReference.update("dateEnd",dateEnd);
        documentReference.update("duration",duration);

        Toast.makeText(getContext(),"Prescription Updated",Toast.LENGTH_SHORT).show();

        if (!getArguments().containsKey("doctor")) {
            documentReference.update("doctorName",doctorName);
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new PrescriptionFragment()).commit();

        } else {
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,new PatientPrescriptionFragment()).commit();
        }

    }
}
