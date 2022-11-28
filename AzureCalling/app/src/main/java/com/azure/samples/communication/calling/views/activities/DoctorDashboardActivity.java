// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.azure.samples.communication.calling.R;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorDashboardActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    static String emailkey = "email_key";

    // key for storing password.
    static String passkey = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    FirebaseAuth mAuth;
    String email;

    CardView cardHome;
    CardView cardScheduleAppoint;
    CardView cardCompleteAppoint;
    CardView cardVideoConf;
    CardView cardLogout;
    CardView cardwaiting;
    EditText drname;
    private String locdrname;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAuth = FirebaseAuth.getInstance();


        cardHome = findViewById(R.id.cardHome);
        cardScheduleAppoint = findViewById(R.id.cardScheduleAppoint);
        cardCompleteAppoint = findViewById(R.id.cardCompleteAppoint);
        cardVideoConf = findViewById(R.id.cardVideoConf);
        cardLogout = findViewById(R.id.cardLogout);
        cardwaiting = findViewById(R.id.cardwaiting);
        //Added by Deepak
        drname = findViewById(R.id.drname);

        final String locdrname = VCHLoginActivity.doctorname;
        drname.setText("Dr. " + locdrname.toUpperCase().charAt(0) + locdrname.substring(1).toLowerCase());

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        email = sharedpreferences.getString("email_key", null);

        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openDactorDashboardActivity();

            }
        });

        cardScheduleAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openDoctorAppointmentActivity();

            }
        });

        cardCompleteAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openCompletePatientActivity();

            }
        });

        cardVideoConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View  view) {
                openIntroViewActivity();
            }
        });

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mAuth.signOut();
                signOutUser();

            }
        });

        cardwaiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openCompleteActivity();

            }
        });

    }

    public void openDactorDashboardActivity() {
        final Intent intent = new Intent(this, DoctorDashboardActivity.class);
        startActivity(intent);
    }

    public void openDoctorAppointmentActivity() {
        final Intent intent = new Intent(this, DoctorAppointmentActivity.class);
        startActivity(intent);
    }

    public void openCompletePatientActivity() {
        final Intent intent = new Intent(this, CompletePatientActivity.class);
        startActivity(intent);
    }

    public void openIntroViewActivity() {
        Toast.makeText(this, "Emergency conference is under construction", Toast.LENGTH_SHORT).show();

    }

    public void signOutUser() {
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        // below line will clear
        // the data in shared prefs.
        editor.clear();
        sharedpreferences.getString("subject_key", null);
        VCHLoginActivity.roll = null;
        // below line will apply empty
        // data to shared prefs.
        editor.apply();

        final Intent intent = new Intent(this, VCHLoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void openCompleteActivity() {
        final Intent intent = new Intent(this, CompleteAppointmentActivity.class);
        startActivity(intent);
    }
}

