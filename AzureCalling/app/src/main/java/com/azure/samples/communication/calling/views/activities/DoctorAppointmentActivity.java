// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.azure.samples.communication.calling.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class DoctorAppointmentActivity extends AppCompatActivity {
    RecyclerView recview5;
    MyAdapter5 adapter5;
    DBmain dBmain;
    DatabaseReference databaseReference;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recview5 = (RecyclerView) findViewById(R.id.recview5);
        recview5.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient_information");

        final Query query = databaseReference.orderByChild("status").equalTo("Intialize");

        final FirebaseRecyclerOptions<Dataholder> options =
                new FirebaseRecyclerOptions.Builder<Dataholder>()
                        .setQuery(query, Dataholder.class)
                        .build();

        adapter5 = new MyAdapter5(options);
        recview5.setAdapter(adapter5);

        modelArrayList = new ArrayList<>();
        dBmain = new DBmain(this);
    }

    protected void onStart() {
        super.onStart();
        adapter5.startListening();
    }

}




