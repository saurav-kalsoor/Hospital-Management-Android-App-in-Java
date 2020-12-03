package com.example.tatwa10.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.ModelClass.Doctor;
import com.example.tatwa10.MainActivity;
import com.example.tatwa10.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class BookAppointmentFragment extends Fragment {

    private ProgressDialog dialog;
    private Spinner spinnerDoctorsList;
    private Spinner spinnerTimeSlots;
    private Button buttonPayment;
    private String docName = "";
    private Button buttonToday;
    private Button buttonTomorrow;
    private boolean today;
    private List<Boolean> listToday;
    private List<Boolean> listTomorrow;
    private Doctor doctor;
    private String doctorId;
    private String authNumber;
    private Calendar time;
    private Calendar currentTime;
    public static final String[] times = new String[]{"10:00 - 10:15 AM","10:15 - 10:30 AM","10:30 - 10:45 AM","10:45 - 11:00 AM","11:00 - 11:15 AM","11:15 - 11:30 AM","11:30 - 11:45 AM","11:45 - 12:00 PM","12:00 - 12:15 PM","12:15 - 12:30 PM","12:30 - 12:45 PM","12:45 - 1:00 PM"};
    public List<String> timesList = Arrays.asList(times);
    public static final List<Calendar> calendarTimesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.navigationView.setCheckedItem(R.id.nav_appointment);
        MainActivity.currentFragment = "bookAppointment";
        View view = inflater.inflate(R.layout.fragment_book_appointment,container,false);

        time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY,10);
        time.set(Calendar.MINUTE,0);
        time.set(Calendar.SECOND,0);
        currentTime = Calendar.getInstance();


        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            authNumber = "+918147921339";
        } else {
            authNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }

        today = true;

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading Available Slots...");
        spinnerDoctorsList = view.findViewById(R.id.spinner_appointment_doctors);
        spinnerTimeSlots = view.findViewById(R.id.spinner_time_slots);
        buttonPayment = view.findViewById(R.id.button_proceed_payment);
        buttonToday = view.findViewById(R.id.button_appointment_today);
        buttonTomorrow = view.findViewById(R.id.button_appointment_tomorrow);
        //recyclerView = view.findViewById(R.id.recycler_view_time_slots);



        String[] names = getContext().getResources().getStringArray(R.array.doctors_name);
        ArrayAdapter<String> adapterNames = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,names);
        adapterNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctorsList.setAdapter(adapterNames);

        loadSpinner();

        if(getArguments() != null) {
            String doctorName = getArguments().getString("docName");
            int position = adapterNames.getPosition(doctorName);
            spinnerDoctorsList.setSelection(position);
        }


        spinnerDoctorsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayClicked();
            }
        });

        buttonTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomorrowClicked();
            }
        });

        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAppointment();
//                docName = spinnerDoctorsList.getSelectedItem().toString();
//                if(TextUtils.isEmpty(docName) || TextUtils.isEmpty(appointmentDate) || TextUtils.isEmpty(appointmentTime)) {
//                    Toast.makeText(getContext(),"Fields Are Empty",Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    Intent intent = new Intent(getActivity(),PaymentActivity.class);
//                    intent.putExtra("docName",docName);
//                    intent.putExtra("date",appointmentDate);
//                    intent.putExtra("time",appointmentTime);
//                    startActivity(intent);
//                }
            }
        });



        return view;
    }

    private void loadSpinner() {
        dialog.show();
        docName = spinnerDoctorsList.getSelectedItem().toString();
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Doctors");
        collectionReference.whereEqualTo("name",docName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    doctor = doc.toObject(Doctor.class);
                    doctorId = doc.getId();
                    break;
                }
                listToday =  doctor.getSlotToday();
                listTomorrow = doctor.getSlotTomorrow();
                continueLoading();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error Loading Slots "+e.toString(),Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

    }

    private void continueLoading() {
        time.set(Calendar.HOUR_OF_DAY,10);
        time.set(Calendar.MINUTE,0);
        time.set(Calendar.SECOND,0);
        List<String > listSlots = new ArrayList<>();
        if (today) {
            for (int i = 0;i<12;i++) {
                currentTime = Calendar.getInstance();
                if (!listToday.get(i) && time.compareTo(currentTime)>0) {
                    listSlots.add(times[i]);
                }
                time.add(Calendar.MINUTE,15);
            }
            if (listSlots.size() == 0) {
                Toast.makeText(getContext(),"Sorry No Slots Available Today",Toast.LENGTH_LONG).show();
                listSlots.add("No Slots Available");
            }
            ArrayAdapter<String> adapterSlots = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item,listSlots);
            adapterSlots.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTimeSlots.setAdapter(adapterSlots);
            dialog.cancel();
        } else {
            for (int i = 0;i<12;i++) {
                if (!listTomorrow.get(i)) {
                    listSlots.add(times[i]);
                }
            }
            if (listSlots.size() == 0) {
                Toast.makeText(getContext(),"Sorry No Slots Available Today",Toast.LENGTH_LONG).show();
                listSlots.add("No Slots Available");
            }
            ArrayAdapter<String> adapterSlots = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item,listSlots);
            adapterSlots.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTimeSlots.setAdapter(adapterSlots);
            dialog.cancel();
        }
    }

    private void tomorrowClicked() {
        today = false;
        buttonTomorrow.setBackground(getResources().getDrawable(R.drawable.custom_button_border_2));
        buttonToday.setBackground(getResources().getDrawable(R.drawable.custom_button_border_3));
        loadSpinner();
    }

    private void todayClicked() {
        today = true;
        buttonToday.setBackground(getResources().getDrawable(R.drawable.custom_button_border_2));
        buttonTomorrow.setBackground(getResources().getDrawable(R.drawable.custom_button_border_3));
        loadSpinner();
    }


    private void updateDoctorSchedule(String name,int index) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Doctors");
        if (today) {
            listToday.set(index,true);
            collectionReference.document(doctorId).update("slotToday",listToday);
            dialog.cancel();
        } else {
            listTomorrow.set(index,true);
            collectionReference.document(doctorId).update("slotTomorrow",listTomorrow);
            dialog.cancel();
        }
    }

    private void saveAppointment() {
        String name = spinnerDoctorsList.getSelectedItem().toString();
        String appointmentDate;
        if(today){
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("E,dd MMM yyyy");
            appointmentDate = format.format(calendar.getTime());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,1);
            SimpleDateFormat format = new SimpleDateFormat("E,dd MMM yyyy");
            appointmentDate = format.format(calendar.getTime());
        }
        String appointmentTime = spinnerTimeSlots.getSelectedItem().toString();
        int timeSlot = timesList.indexOf(appointmentTime);
        if(timeSlot == -1){
            Toast.makeText(getContext(),"Please Choose A Slot",Toast.LENGTH_SHORT).show();
        } else {
            Appointment appointment = new Appointment(authNumber,name,appointmentDate, appointmentTime,false,false,MainActivity.patientName);
            CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Appointments");
            collectionReference.add(appointment);
            updateDoctorSchedule(name,timeSlot);
            Toast.makeText(getContext(),"Appointment Booked",Toast.LENGTH_SHORT).show();
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new AppointmentFragment()).commit();
        }
    }

}


//    private void openTimePicker() {
//        DialogFragment newFragment  = new TimePickerFragment(new TimePickerFragment.SetOnTimeSet() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onTimeSet(TimePicker view) {
//                int hour = view.getHour();
//                int minute = view.getMinute();
//                Time time = new Time(hour,minute,0);
//                DateFormat timeFormat = new SimpleDateFormat("h:mm a");
//                appointmentTime = timeFormat.format(time);
//                textViewSelectTime.setText(appointmentTime);
//
//
//            }
//
//        });
//        newFragment.show(getChildFragmentManager(),null);
//    }

//    private void openDatePicker() {
//        DatePickerFragment date = new DatePickerFragment();
//        final Calendar calender = Calendar.getInstance();
//        Bundle args = new Bundle();
//        args.putInt("year", calender.get(Calendar.YEAR));
//        args.putInt("month", calender.get(Calendar.MONTH));
//        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
//        date.setArguments(args);
//
//        DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
//
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                calendarDate.set(Calendar.YEAR,year);
//                calendarDate.set(Calendar.MONTH,monthOfYear);
//                calendarDate.set(Calendar.DATE,dayOfMonth);
//                DateFormat dateFormatStart = new SimpleDateFormat("E ,dd MMM yyyy");
//                appointmentDate = dateFormatStart.format(calendarDate.getTime());
//                textViewDate.setText(appointmentDate);
//            }
//        };
//
//        date.setCallBack(onDate);
//        date.show(getParentFragmentManager(), "Date Picker");
//
//    }