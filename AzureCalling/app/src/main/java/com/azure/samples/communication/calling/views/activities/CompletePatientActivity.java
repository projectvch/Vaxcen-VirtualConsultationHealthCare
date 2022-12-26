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

public class CompletePatientActivity extends AppCompatActivity {
    RecyclerView recview3;
    MyAdapter3 adapter3;
    DBmain dBmain;
    DatabaseReference databaseReference;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_patient);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recview3 = (RecyclerView) findViewById(R.id.recview3);
        recview3.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient_information");

        final Query query = databaseReference.orderByChild("status").equalTo("completed");

        final FirebaseRecyclerOptions<Dataholder> options =
                new FirebaseRecyclerOptions.Builder<Dataholder>()
                        .setQuery(query,
                                Dataholder.class)
                        .build();

        adapter3 = new MyAdapter3(options);
        recview3.setAdapter(adapter3);

        modelArrayList = new ArrayList<>();
        dBmain = new DBmain(this);
    }

    protected void onStart() {
        super.onStart();
        adapter3.startListening();
    }
}




