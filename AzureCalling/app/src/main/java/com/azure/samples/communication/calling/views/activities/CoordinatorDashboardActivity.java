// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.azure.samples.communication.calling.R;

public class CoordinatorDashboardActivity extends AppCompatActivity {

    CardView cardHome;
    CardView cardScheduleAppoint;
    CardView cardCompleteAppoint;
    CardView cardPatientForm;
    CardView cardVideoConf;
    CardView cardLogout;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_dashboard);
        getSupportActionBar().hide();

        cardHome = findViewById(R.id.cardHome);
        cardScheduleAppoint = findViewById(R.id.cardScheduleAppoint);
        cardCompleteAppoint = findViewById(R.id.cardCompleteAppoint);
        cardPatientForm = findViewById(R.id.cardPatientForm);
        cardVideoConf = findViewById(R.id.cardVideoConf);
        cardLogout = findViewById(R.id.cardLogout);

        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openCoordiantorDashboardActivity();

            }
        });

        cardScheduleAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openMainActivity();

            }
        });

        cardCompleteAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openCompletePatientActivity();

            }
        });

        cardPatientForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openPatientFormActivity();

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
                openLoginActivity();

            }
        });

    }

    public void openCoordiantorDashboardActivity() {
        final Intent intent = new Intent(this, CoordinatorRoomActivity.class);
        startActivity(intent);
    }

    public void openMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openCompletePatientActivity() {
        final Intent intent = new Intent(this, CompletePatientActivity.class);
        startActivity(intent);
    }

    public void openPatientFormActivity() {
        final Intent intent = new Intent(this, PatientFormActivity.class);
        startActivity(intent);
    }

    public void openIntroViewActivity() {
        Toast.makeText(this, "Emergency conference is under construction", Toast.LENGTH_SHORT).show();
    }

    public void openLoginActivity() {
        final Intent intent = new Intent(this, VCHLoginActivity.class);
        startActivity(intent);
    }
}
