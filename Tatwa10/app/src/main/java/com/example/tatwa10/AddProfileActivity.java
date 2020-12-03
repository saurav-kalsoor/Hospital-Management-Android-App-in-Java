package com.example.tatwa10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatwa10.ModelClass.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProfileActivity extends AppCompatActivity {

    private Button buttonContinueSave;
    private CircleImageView imageViewProfileImage;
    private EditText editTextProfileName;
    private EditText editTextProfileEmail;
    private TextView textViewProfilePhoneNumber;
    private RadioGroup radioGroupProfileSex;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private EditText editTextProfileAge;
    private Uri imageUri;
    private String authNumber;
    private Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);



        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            authNumber = "+918147921339";
        } else {
            authNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }

        buttonContinueSave = findViewById(R.id.button_profile_continue_save);
        buttonContinueSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });



        imageViewProfileImage = findViewById(R.id.image_view_profile);
        editTextProfileName = findViewById(R.id.edit_text_profile_name);
        editTextProfileEmail = findViewById(R.id.edit_text_profile_email);
        textViewProfilePhoneNumber = findViewById(R.id.text_view_profile_number);
        radioGroupProfileSex = findViewById(R.id.radio_group_profile_sex);
        radioButtonMale = findViewById(R.id.radio_button_profile_male);
        radioButtonFemale = findViewById(R.id.radio_button_profile_female);
        editTextProfileAge = findViewById(R.id.edit_text_profile_age);

        String number = "Phone Number :   " + authNumber;
        textViewProfilePhoneNumber.setText(number);

//        StorageReference ref = FirebaseStorage.getInstance().getReference("PatientImages").child(authNumber + ".jpeg");
//        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Got the download URL for 'users/me/profile.png'
//                Picasso.get().load(uri).into(imageViewProfileImage);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });

        imageViewProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(AddProfileActivity.this);
            }
        });
    }

    private void saveProfile() {
        final String name = editTextProfileName.getText().toString().trim() + "";
        final String email = editTextProfileEmail.getText().toString().trim() + "";
        final boolean sex = radioGroupProfileSex.getCheckedRadioButtonId() == R.id.radio_button_profile_male;
        final String age = editTextProfileAge.getText().toString() + "";
        final String phone = authNumber;

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(age)) {
            Toast.makeText(AddProfileActivity.this,"Fields are Empty, please fill to proceed",Toast.LENGTH_SHORT).show();
            return;
        } else if (imageUri == null) {
            Toast.makeText(AddProfileActivity.this,"Please Add an Image",Toast.LENGTH_SHORT).show();
            return;
        } else {

            StorageReference filepath = FirebaseStorage.getInstance().getReference("PatientImages").child(authNumber+".jpeg");
            filepath.putFile(imageUri);
            profile = new Profile(name,email,phone,sex,age);
            CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Patients");
            collectionReference.add(profile);
            Toast.makeText(AddProfileActivity.this,"Profile Created Successfully",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddProfileActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imageViewProfileImage.setImageURI(imageUri);
        } else {
            Toast.makeText(AddProfileActivity.this,"Error Occurred while loading Image",Toast.LENGTH_SHORT).show();
        }

    }



}
