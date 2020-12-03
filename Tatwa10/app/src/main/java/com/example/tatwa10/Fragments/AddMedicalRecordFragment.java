package com.example.tatwa10.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.MainActivity;
import com.example.tatwa10.ModelClass.MedicalRecord;
import com.example.tatwa10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddMedicalRecordFragment extends Fragment {

    private EditText editTextRecordTitle;
    private Button buttonChooseFile;
    private Button buttonUploadFile;
    private Uri imageUri;
    private static final int GALLERY_REQUEST_CODE = 3;
    private String authNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.navigationView.setCheckedItem(R.id.nav_upload_doc);
        MainActivity.currentFragment = "addMedicalRecord";
        View view = inflater.inflate(R.layout.fragment_add_medical_records, container, false);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            authNumber = "+918147921339";
        } else {
            authNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }

        editTextRecordTitle = view.findViewById(R.id.edit_text_add_medical_record_title);
        buttonChooseFile = view.findViewById(R.id.button_choose_file_medical_records);
        buttonUploadFile = view.findViewById(R.id.button_upload_document_medical_records);

        buttonChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        buttonUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });


        return view;
    }


    private void chooseFile() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            imageUri = data.getData();
            Toast.makeText(getContext(), "File Received", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile() {
        String title = editTextRecordTitle.getText().toString().trim() + "";
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getContext(), "Title is Empty", Toast.LENGTH_SHORT).show();
            return;

        }
        if (imageUri == null) {
            Toast.makeText(getContext(), "Image is Empty", Toast.LENGTH_SHORT).show();
            return;

        }
        StorageReference filepath = FirebaseStorage.getInstance().getReference("documents/" + authNumber).child(title + ".jpeg");
        filepath.putFile(imageUri);
        MedicalRecord medicalRecord = new MedicalRecord(title, authNumber);
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("MedicalRecords");
        collectionReference.add(medicalRecord);
        Toast.makeText(getContext(), "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();


    }

}
