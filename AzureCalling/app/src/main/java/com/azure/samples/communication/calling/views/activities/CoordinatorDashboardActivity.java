// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.azure.samples.communication.calling.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Map;

public class CoordinatorDashboardActivity extends AppCompatActivity {

    // variable for shared preferences.
    ImageView notification;
    FirebaseAuth mAuth;
    CardView cardHome;
    CardView cardScheduleAppoint;
    CardView cardCompleteAppoint;
    CardView cardPatientForm;
    CardView cardVideoConf;
    CardView cardLogout;
    ListView lstview;
    EditText cordname;

    private String loccordname;






    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_dashboard);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();

        cardHome = findViewById(R.id.cardHome);
        cardScheduleAppoint = findViewById(R.id.cardScheduleAppoint);
        cardCompleteAppoint = findViewById(R.id.cardCompleteAppoint);
        cardPatientForm = findViewById(R.id.cardPatientForm);
        cardVideoConf = findViewById(R.id.cardVideoConf);
        cardLogout = findViewById(R.id.cardLogout);
        //Added by Deepak
        cordname = findViewById(R.id.cordname);
        notification = findViewById(R.id.cb_notification);
        lstview = findViewById(R.id.lv_notification);


        final String loccordname = VCHLoginActivity.regby;
        cordname.setText(loccordname.toUpperCase().charAt(0) + loccordname.substring(1).toLowerCase());


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
                mAuth.signOut();
                signOutUser();

            }
        });

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                //final String text = (String) parent.getItemAtPosition(position);
                openintroview2activity();
                lstview.setVisibility(View.INVISIBLE);
                //final Intent intent = new Intent(this, IntroView2Activity.class);
                Toast.makeText(CoordinatorDashboardActivity.this, "Clicked Notification", Toast.LENGTH_LONG).show();
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                lstview.setVisibility(lstview.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                getdata();
                //Toast.makeText(CoordinatorDashboardActivity.this, "Clicked Notification", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getdata() {
        //final ListView lstview = (ListView) findViewById(R.id.lv_notification);
        List<Map<String, String>> mydatalist = null;
        final ListItem mydata = new ListItem();
        mydatalist = mydata.getlist();
        final String[] fromw = {"cordinatorname", "patientname"};
        final int[] tow = {R.id.cordinatorname, R.id.patientname};
        final SimpleAdapter ad;
        ad = new SimpleAdapter(CoordinatorDashboardActivity.this, mydatalist, R.layout.listlayouttemplete, fromw, tow);
        lstview.setAdapter(ad);


    }

    //Added by Deepak

    public void openintroview2activity() {
        final Intent intent = new Intent(this, IntroView2Activity.class);
        startActivity(intent);
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

    public void signOutUser() {
        VCHLoginActivity.roll = null;

        final Intent intent = new Intent(this, VCHLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
