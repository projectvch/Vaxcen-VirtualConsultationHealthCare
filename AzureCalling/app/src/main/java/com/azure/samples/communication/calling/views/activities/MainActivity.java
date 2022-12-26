// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.azure.samples.communication.calling.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton homebutton;
    RecyclerView recview;
    MyAdapter adapter;
    DBmain dBmain;
    DatabaseReference databaseReference;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recview = (RecyclerView) findViewById(R.id.recview);
        homebutton = (ImageButton) findViewById(R.id.homebutton);
        recview.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient_information");



        final Query query = databaseReference.orderByChild("status").equalTo("Intialize");

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                opendashboard();
            }
        });

        final FirebaseRecyclerOptions<Dataholder> options =
                new FirebaseRecyclerOptions.Builder<Dataholder>()
                        .setQuery(query, Dataholder.class)
                        .build();

        adapter = new MyAdapter(options);
        recview.setAdapter(adapter);

        modelArrayList = new ArrayList<>();
        dBmain = new DBmain(this);
    }

    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void opendashboard() {
        final Intent intent = new Intent(this, CoordinatorDashboardActivity.class);
        startActivity(intent);
    }

}




