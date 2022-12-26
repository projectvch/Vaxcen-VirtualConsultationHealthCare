// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.ResultSet;
import java.sql.Statement;
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
    EditText name, phone, address, email, lastname, middlename, birthdate, register, temperature, age;
    RadioButton rbfeaveryes, rbfeaverno;
    RadioButton rbsmokeyes, rbsmokeno;
    RadioButton rballergicyes, rballergicno;
    RadioButton rbhivyes, rbhivno;
    RadioButton rbimmunoglobyes, rbimmunoglobno;
    RadioButton rbpregyes, rbpregno;
    Button browse;
    Button submit, open;
    Uri imageUri;
    String pimage1;
    String dob;
    String ageyear;
    String feaveryesno = "No";
    String smokeyesno = "No";
    String allergicyesno = "No";
    String hivyesno = "No";
    String immunoglobyesno = "No";
    String pregyesno = "No";

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        img = (ImageView) findViewById(R.id.img);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        lastname = findViewById(R.id.lastname);
        middlename = findViewById(R.id.middlename);
        birthdate = findViewById(R.id.birthdate);
        register = findViewById(R.id.register);
        browse = findViewById(R.id.browse);
        submit = findViewById(R.id.submit);
        open = findViewById(R.id.open);
        storage = FirebaseStorage.getInstance();
        age = findViewById(R.id.age);
        temperature = findViewById(R.id.temperature);
        //Added by Deepak
        rbfeaveryes = findViewById(R.id.radio0);
        rbfeaverno = findViewById(R.id.radio1);
        rbsmokeyes = findViewById(R.id.smoke1);
        rbsmokeno = findViewById(R.id.smoke2);
        rballergicyes = findViewById(R.id.preg);
        rballergicno = findViewById(R.id.preg1);
        rbhivyes = findViewById(R.id.alcohol1);
        rbhivno = findViewById(R.id.alchol2);
        rbimmunoglobyes = findViewById(R.id.immunoglobulin);
        rbimmunoglobno = findViewById(R.id.immunoglobulin2);
        rbpregyes = findViewById(R.id.Preg1);
        rbpregno = findViewById(R.id.Preg2);
        birthdate.setInputType(InputType.TYPE_NULL);
        final Calendar calendar = Calendar.getInstance();
        //Added by Deepak
        register.setText(VCHLoginActivity.regby);

        birthdate.setOnClickListener(new View.OnClickListener() {
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
                        birthdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, cyear, cmonth, cday);
                datePicker.show();

                //Added by Deepak



                birthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(final View v, final boolean hasFocus) {
                        if (!hasFocus) {
                            // code to execute when EditText loses focus
                            //edittext2.setText("Lost Focus");
                            final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy",
                                    Locale.ENGLISH);
                            //final SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                            final Date todaysdate = Calendar.getInstance().getTime();
                            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            final String strDate = dateFormat.format(todaysdate);
                            dob = String.valueOf(birthdate.getText());
                            Date enddate = null;
                            try {
                                enddate = sdf1.parse(dob);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            final String strendDate = dateFormat.format(enddate);
                            final LocalDate sdate = LocalDate.parse(strDate);
                            final LocalDate pdate = LocalDate.parse(strendDate);
                            final LocalDate ssdate = LocalDate.of(sdate.getYear(), sdate.getMonth(),
                                    +sdate.getDayOfMonth());
                            final LocalDate ppdate = LocalDate.of(pdate.getYear(), pdate.getMonth(),
                                    +pdate.getDayOfMonth());
                            //final Period period = Period.between(ssdate, ppdate);
                            final Period period = Period.between(ppdate, ssdate);
                            final long noofyears = period.getYears();
                            final long noofmonths = period.getMonths();
                            final long noofdays = period.getDays();
                            ageyear = String.valueOf(noofyears);
                            /*age.setText(String.valueOf(noofyears) + " years " + String.valueOf(noofmonths)
                                    + " months " + String.valueOf(noofdays) + " days");*/
                            age.setText(String.valueOf(noofyears) + " years ");
                        }
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
                } else if (TextUtils.isEmpty(lastname.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(birthdate.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter date of birth", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(age.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter age", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(PatientFormActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else {
                    formComplete();
                    open.setVisibility(View.INVISIBLE);
                }

            }
        });

        //Added by Deepak
        rbfeaveryes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    feaveryesno = "Yes";
                }

            }
        });

        rbfeaverno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    feaveryesno = "No";
                }

            }
        });

        rbsmokeyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    smokeyesno = "Yes";
                }

            }
        });

        rbsmokeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    smokeyesno = "No";
                }

            }
        });

        rballergicyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    allergicyesno = "Yes";
                }

            }
        });

        rballergicno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    allergicyesno = "No";
                }

            }
        });

        rbhivyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    hivyesno = "Yes";
                }

            }
        });

        rbhivno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    hivyesno = "No";
                }

            }
        });

        rbimmunoglobyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    immunoglobyesno = "Yes";
                }

            }
        });

        rbimmunoglobno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    immunoglobyesno = "No";
                }

            }
        });

        rbpregyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    pregyesno = "Yes";
                }

            }
        });

        rbpregno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean checked = ((RadioButton) view).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
                    // Do your coding
                    pregyesno = "No";
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
        databaseReference = rootnode.getReference().child("patient_information");

        final String register1 = register.getText().toString();
        final String name1 = name.getText().toString();
        final String phone1 = phone.getText().toString();
        final String address1 = address.getText().toString();
        final String email1 = email.getText().toString();
        final String lastname1 = lastname.getText().toString();
        final String middlename1 = middlename.getText().toString();
        final String birthdate1 = birthdate.getText().toString();
        final String feaver = feaveryesno;
        final String vaccine = smokeyesno;
        final String allergic = allergicyesno;
        final String hiv = hivyesno;
        final String pregnant = pregyesno;
        final String immunoglob = immunoglobyesno;
        final String status = "Intialize";
        final String date = DateFormat.getInstance().format(System.currentTimeMillis());
        final String id = databaseReference.push().getKey();

        final Dataholder dataholder = new Dataholder();
        dataholder.setDate(date);
        dataholder.setName(name1);
        dataholder.setMiddlename(middlename1);
        dataholder.setLastname(lastname1);
        dataholder.setAge(ageyear);
        dataholder.setAddress(address1);
        dataholder.setPhone(phone1);
        dataholder.setEmail(email1);
        dataholder.setPimage(pimage1);
        dataholder.setRegister(register1);
        dataholder.setStatus(status);
        dataholder.setFeaver(feaver);
        dataholder.setVaccine(vaccine);
        dataholder.setAllergic(allergic);
        dataholder.setHiv(hiv);
        dataholder.setImmunoglob(immunoglob);
        dataholder.setPregnant(pregnant);

        dataholder.setWaitingdate("");
        databaseReference.child(id).setValue(dataholder);
        birthdate.setText("");
        name.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        lastname.setText("");
        middlename.setText("");
        age.setText("");
        temperature.setText("");
        rbfeaverno.setChecked(true);
        rbsmokeno.setChecked(true);
        rballergicno.setChecked(true);
        rbhivno.setChecked(true);
        rbimmunoglobno.setChecked(true);
        rbpregno.setChecked(true);

        img.setImageResource(R.drawable.ic_launcher_background);
        //Added by Deepak
        inserttotable(name1, date);
        //Toast.makeText(PatientFormActivity.this, "Patient is Successfully Registered", Toast.LENGTH_LONG).show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(PatientFormActivity.this);
        builder.setView(R.layout.patient_success_message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                final Intent intent = new Intent(PatientFormActivity.this, CoordinatorDashboardActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
    public void inserttotable(final String name1, final String regdate) {


        final String locregby = VCHLoginActivity.regby;
        final String loctoken = VCHLoginActivity.tokenstore;
        final String locstatus = "Waiting";
        try {

            final ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {

                final String sqlinsert = "Insert into dbo.tokenstorage (coordinatorname,patientname,token,"
                      +  "status,regdate)"
                        + " values ('"
                        + locregby +  "','" + name1 + "','" + loctoken + "','" + locstatus + "','" + regdate + "')";

                final Statement st = connect.createStatement();
                final ResultSet rs = st.executeQuery(sqlinsert);

            } else {
                connectionresult = "Check Connection";
            }

        } catch (Exception ex) {

        }

    }







}

