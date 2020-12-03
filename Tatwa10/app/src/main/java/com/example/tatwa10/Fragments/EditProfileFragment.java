package com.example.tatwa10.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.MainActivity;
import com.example.tatwa10.ModelClass.Profile;
import com.example.tatwa10.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 12;
    private CircleImageView imageViewProfileImage;
    private EditText editTextProfileName;
    private EditText editTextProfileEmail;
    private TextView textViewProfileNumber;
    private RadioGroup radioGroupSex;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private EditText editTextProfileAge;
    private Button buttonContinueSave;
    private Profile profile;
    private Uri imageUri;
    private String documentId;
    private String phone;
    private ProgressDialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_profile, container, false);
        MainActivity.currentFragment = "editProfile";
        MainActivity.navigationView.setCheckedItem(R.id.nav_edit_profile);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            phone = "+918147921339";
        } else {
            phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();
        dialog.setCancelable(false);


        imageViewProfileImage = view.findViewById(R.id.image_view_profile);
        editTextProfileName = view.findViewById(R.id.edit_text_profile_name);
        editTextProfileEmail = view.findViewById(R.id.edit_text_profile_email);
        textViewProfileNumber = view.findViewById(R.id.text_view_profile_number);
        radioGroupSex = view.findViewById(R.id.radio_group_profile_sex);
        radioButtonMale = view.findViewById(R.id.radio_button_profile_male);
        radioButtonFemale = view.findViewById(R.id.radio_button_profile_female);
        editTextProfileAge = view.findViewById(R.id.edit_text_profile_age);
        buttonContinueSave = view.findViewById(R.id.button_profile_continue_save);
        buttonContinueSave.setText("Save");

        textViewProfileNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Phone Number cannot be changed",Toast.LENGTH_SHORT).show();
            }
        });

        imageViewProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);

            }
        });

        buttonContinueSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        getReference();


        return view;
    }

    private void getReference() {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Patients");
        collectionReference.whereEqualTo("phone",phone).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null) {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        documentId = doc.getId();
                        profile = doc.toObject(Profile.class);
                        break;
                    }
                    setUpProfile();
                } else {
                    Toast.makeText(getContext(),"Profile is Empty",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void setUpProfile() {


        editTextProfileName.setText(profile.getName());
        editTextProfileEmail.setText(profile.getEmail());

        String number = "Phone Number :   "+phone;
        textViewProfileNumber.setText(number);

        if(profile.isSex()) {
            radioButtonMale.setChecked(true);
        } else {
            radioButtonFemale.setChecked(true);
        }

        StorageReference ref = FirebaseStorage.getInstance().getReference("PatientImages").child(phone+".jpeg");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageViewProfileImage);
                dialog.cancel();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        });
        editTextProfileAge.setText(profile.getAge());

    }

    private void saveProfile() {
        String name = editTextProfileName.getText().toString().trim()+"";
        String email = editTextProfileEmail.getText().toString().trim()+"";
        String age = editTextProfileAge.getText().toString().trim()+"";
        boolean sex = radioGroupSex.getCheckedRadioButtonId() == R.id.radio_button_profile_male;

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(age)) {
            Toast.makeText(getContext(),"Fields Are Empty",Toast.LENGTH_SHORT).show();
            return;
        }

        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Patients").document(documentId);
        documentReference.update("name",name);
        documentReference.update("email",email);
        documentReference.update("age",age);
        documentReference.update("sex",sex);


        StorageReference filepath = FirebaseStorage.getInstance().getReference("PatientImages").child(phone+".jpeg");
        if(imageUri != null ) {
            filepath.putFile(imageUri);
        }
        Toast.makeText(getContext(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            imageUri = data.getData();
            imageViewProfileImage.setImageURI(imageUri);
            Toast.makeText(getContext(),"Image Loaded",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),"Image Loading Failed,Try Again",Toast.LENGTH_SHORT).show();
        }
    }
}
