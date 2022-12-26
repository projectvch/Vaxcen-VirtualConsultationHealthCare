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

public class CoordinatorRoomActivity extends AppCompatActivity {
    RecyclerView recview4;
    MyAdapter4 adapter4;
    DBmain dBmain;
    DatabaseReference databaseReference;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_room);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recview4 = (RecyclerView) findViewById(R.id.recview4);
        recview4.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient_information");

        final Query query = databaseReference.orderByChild("status").equalTo("waiting");

        final FirebaseRecyclerOptions<Dataholder> options =
                new FirebaseRecyclerOptions.Builder<Dataholder>()
                        .setQuery(query, Dataholder.class)
                        .build();

        adapter4 = new MyAdapter4(options);
        recview4.setAdapter(adapter4);

        modelArrayList = new ArrayList<>();
        dBmain = new DBmain(this);
    }

    protected void onStart() {
        super.onStart();
        adapter4.startListening();
    }

}
