// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.azure.samples.communication.calling.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import java.sql.Connection;


public class PatientFormActivity extends AppCompatActivity {
    Connection connect;
    String connectionresult = "";
    EditText edittext2;
    int cyear;
    int cmonth;
    int cday;
    FirebaseStorage storage;
    FirebaseDatabase rootnode;
    DatabaseReference databaseReference;
    StorageReference reference;
    ImageView img;
    EditText name, phone, address, email, disease, symptoms, date, register;
    TextView age;
    Button browse;
    Button submit, open;
    Uri imageUri;
    String pimage1;
    String dob;
    int maxid;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);
        getSupportActionBar().hide();

        img = (ImageView) findViewById(R.id.img);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        disease = findViewById(R.id.disease);
        symptoms = findViewById(R.id.symptoms);
        date = findViewById(R.id.date);
        register = findViewById(R.id.register);
        browse = findViewById(R.id.browse);
        submit = findViewById(R.id.submit);
        open = findViewById(R.id.open);
        storage = FirebaseStorage.getInstance();
        edittext2 = findViewById(R.id.edittext2);

        date.setInputType(InputType.TYPE_NULL);
        final Calendar calendar = Calendar.getInstance();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //Show Current Date
                final Calendar calendar = Calendar.getInstance();
                cyear = calendar.get(Calendar.YEAR);
                cmonth = calendar.get(Calendar.MONTH);
                cday = calendar.get(Calendar.DAY_OF_MONTH);

                //Launch Datepicker Dialog
                final  DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(PatientFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker datePicker,
                                          final int year, final int month, final int dayOfMonth) {
                        cyear = year;
                        cmonth = month;
                        cday = dayOfMonth;
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, cyear, cmonth, cday);
                datePicker.show();

                //Added by Deepak
                edittext2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy",
                                Locale.ENGLISH);
                        //final SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        final Date todaysdate = Calendar.getInstance().getTime();
                        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        final String strDate = dateFormat.format(todaysdate);
                        dob = String.valueOf(date.getText());
                        Date enddate = null;
                        try {
                            enddate = sdf1.parse(dob);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        final String strendDate = dateFormat.format(enddate);
                        final LocalDate sdate = LocalDate.parse(strDate);
                        final LocalDate pdate = LocalDate.parse(strendDate);
                        final LocalDate ssdate = LocalDate.of(sdate.getYear(), sdate.getMonth(), sdate.getDayOfMonth());
                        final LocalDate ppdate = LocalDate.of(pdate.getYear(), pdate.getMonth(), pdate.getDayOfMonth());
                        //final Period period = Period.between(ssdate, ppdate);
                        final Period period = Period.between(ppdate, ssdate);
                        final long noofyears = period.getYears();
                        final long noofmonths = period.getMonths();
                        final long noofdays = period.getDays();
                        edittext2.setText(String.valueOf(noofyears) + " years " + String.valueOf(noofmonths)
                                + " months " + String.valueOf(noofdays) + " days");

                    }

                });



                /* edittext2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                        final int ageyear = currentYear - cyear;


                        edittext2.setText(String.valueOf(ageyear) + " Years");
                    }
                }); */
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (TextUtils.isEmpty(register.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter Registered By", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(disease.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(date.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter date of birth", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edittext2.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter age", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else {
                    formComplete();
                    open.setVisibility(View.VISIBLE);
                }

            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(PatientFormActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // start activity for result (new method)
        final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {

                    @Override
                    public void onActivityResult(final Uri result) {
                        // this result is result of uri
                        if (result != null) {
                            img.setImageURI(result);
                            imageUri = result;
                        }
                    }
                });

        // run the below method or img class
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mGetContent.launch("image/*");
                // image is selected from gallery
                // now we need to upload the image to firebase storage
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // upload image on button click
                uploadImage();
            }
        });
    }

    public void uploadImage() {
        // here we need to access the below result code but we can't
        // so to solved it, we will it as a global
        if (imageUri != null) {
            final StorageReference reference = storage.getReference().child("image/" + System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            // we are creating reference to store image in firebase storage
            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
                                pimage1 = uri.toString();
                            }
                        });
                        Toast.makeText(PatientFormActivity.this,
                                "IMAGE UPLOADED", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PatientFormActivity.this,
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private  String getFileExtension(final Uri imageUri) {
        final ContentResolver cr = getContentResolver();
        final MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(imageUri));
    }
    private void formComplete() {
        final FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        databaseReference = rootnode.getReference().child("newusers");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = (int) snapshot.getChildrenCount();
                    Log.d("value", String.valueOf(maxid));
                }
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError error) {

            }
        });

        final String register1 = register.getText().toString();
        final String name1 = name.getText().toString();
        final String phone1 = phone.getText().toString();
        final String address1 = address.getText().toString();
        final String email1 = email.getText().toString();
        final String disease1 = disease.getText().toString();
        final String symptoms1 = symptoms.getText().toString();
        final String date1 = date.getText().toString();
        final Calendar cal = Calendar.getInstance();
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String calldate = String.valueOf(timestamp.getTime());



        final Dataholder dataholder = new Dataholder(register1, name1, phone1, address1,
                email1, disease1, symptoms1, date1, pimage1);

        Log.d("value", String.valueOf(maxid));
        databaseReference.child(String.valueOf(maxid + 1)).setValue(dataholder);
        register.setText("");
        name.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        disease.setText("");
        symptoms.setText("");
        img.setImageResource(R.drawable.ic_launcher_background);
        //Added by Deepak
        inserttotable(name1);
        Toast.makeText(PatientFormActivity.this, "Patient is Successfully Registered", Toast.LENGTH_LONG).show();
    }
    public void inserttotable(final String name1) {


        final String locregby = VCHLoginActivity.regby;
        final String loctoken = VCHLoginActivity.tokenstore;
        final String locstatus = "Waiting";
        try {

            final ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {

                final String sqlinsert = "Insert into dbo.tokenstorage (coordinatorname,patientname,token,status)"
                        + " values ('"
                        + locregby +  "','" + name1 + "','" + loctoken + "','" + locstatus + "')";

                final Statement st = connect.createStatement();
                final ResultSet rs = st.executeQuery(sqlinsert);

            } else {
                connectionresult = "Check Connection";
            }

        } catch (Exception ex) {

        }

    }







}

