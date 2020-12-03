package com.example.tatwa10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tatwa10.ModelClass.Doctor;

import java.util.ArrayList;
import java.util.List;


public class DoctorDbHelper extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase myDb;

    public static final String DATABASE_NAME = "doctor_database.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "doctors_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "names";
    public static final String COLUMN_SPECIFICATION = "specifications";
    public static final String COLUMN_QUALIFICATION = "qualifications";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGEURI = "imageUri";

    public DoctorDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        myDb = db;
        final String CREATE_TABLE = "CREATE TABLE "+
                TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_SPECIFICATION + " TEXT," +
                COLUMN_QUALIFICATION + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_IMAGEURI + " INTEGER)";
        db.execSQL(CREATE_TABLE);
        myDb.insert(TABLE_NAME,null,null);
        fillDoctorsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void fillDoctorsTable() {
        String[] names = context.getResources().getStringArray(R.array.doctors_name);
        String[] specifications = context.getResources().getStringArray(R.array.doctor_specification_field);
        String[] qualifications = context.getResources().getStringArray(R.array.doctors_qualifications);
        String[] descriptions = context.getResources().getStringArray(R.array.doctors_description);
        int[] imageUri = new int[] {
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.female,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.female,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male,
                R.drawable.male };

        for(int i = 0;i < names.length ; i++) {
            String name = names[i];
            String specification = specifications[i];
            String qualification = qualifications[i];
            String description = descriptions[i];
            int image = imageUri[i];
            Doctor doctor = new Doctor(name,specification,qualification,description,image);
            addDoctor(doctor);
        }
    }

    private void addDoctor(Doctor doctor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,doctor.getName());
        contentValues.put(COLUMN_SPECIFICATION,doctor.getSpecification());
        contentValues.put(COLUMN_QUALIFICATION,doctor.getQualifications());
        contentValues.put(COLUMN_DESCRIPTION,doctor.getDescription());
        contentValues.put(COLUMN_IMAGEURI,doctor.getImageUri());
        myDb.insert(TABLE_NAME,null,contentValues);
    }

    public List<Doctor> getDoctorsList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        List<Doctor> list = new ArrayList<>();

        while(result.moveToNext()) {
            String name = result.getString(1);
            String specification = result.getString(2);
            String qualification = result.getString(3);
            String description = result.getString(4);
            int imageUri = result.getInt(5);
            Doctor doctor = new Doctor(name,specification,qualification,description,imageUri);
            list.add(doctor);
        }
        result.close();
        return list;
    }
}






