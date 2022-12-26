// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import com.azure.samples.communication.calling.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class CompleteAppointmentActivity extends AppCompatActivity {
    RecyclerView recview2;
    MyAdapter2 adapter2;
    DBmain dBmain;
    DatabaseReference databaseReference;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_complete_appointment);
            getSupportActionBar().hide();
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            recview2 = (RecyclerView) findViewById(R.id.recview2);
            recview2.setLayoutManager(new LinearLayoutManager(this));
            databaseReference = FirebaseDatabase.getInstance().getReference().child("patient_information");

            final Query query = databaseReference.orderByChild("status").equalTo("waiting");


            final FirebaseRecyclerOptions<Dataholder> options =
                    new FirebaseRecyclerOptions.Builder<Dataholder>()
                            .setQuery(query,
                                    Dataholder.class)
                            .build();

            adapter2 = new MyAdapter2(options);
            recview2.setAdapter(adapter2);


            modelArrayList = new ArrayList<>();
            dBmain = new DBmain(this);
        } catch (final IndexOutOfBoundsException e) {
            Log.e("TAG", "error" + e.getMessage());
            recview2 = (RecyclerView) findViewById(R.id.recview2);
            recview2.setLayoutManager(new LinearLayoutManager(this));
            databaseReference = FirebaseDatabase.getInstance().getReference().child("patient_information");

            final Query query = databaseReference.orderByChild("status").equalTo("waiting");


            final FirebaseRecyclerOptions<Dataholder> options =
                    new FirebaseRecyclerOptions.Builder<Dataholder>()
                            .setQuery(query,
                                    Dataholder.class)
                            .build();

            adapter2 = new MyAdapter2(options);
            recview2.setAdapter(adapter2);


            modelArrayList = new ArrayList<>();
            dBmain = new DBmain(this);
        }
    }

    protected void onStart() {
        super.onStart();
        adapter2.startListening();
    }

}




