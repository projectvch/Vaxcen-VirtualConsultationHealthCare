// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.azure.samples.communication.calling.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CompleteAppointmentActivity extends AppCompatActivity {
    RecyclerView recview2;
    MyAdapter2 adapter2;
    DBmain dBmain;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_complete_appointment);
            getSupportActionBar().hide();
            recview2 = (RecyclerView) findViewById(R.id.recview2);
            recview2.setLayoutManager(new LinearLayoutManager(this));


            final FirebaseRecyclerOptions<Dataholder> options =
                    new FirebaseRecyclerOptions.Builder<Dataholder>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("comusers"), Dataholder.class)
                            .build();

            adapter2 = new MyAdapter2(options);
            recview2.setAdapter(adapter2);


            modelArrayList = new ArrayList<>();
            dBmain = new DBmain(this);
        } catch (final IndexOutOfBoundsException e) {
            Log.e("TAG", "error" + e.getMessage());
            recview2 = (RecyclerView) findViewById(R.id.recview2);
            recview2.setLayoutManager(new LinearLayoutManager(this));


            final FirebaseRecyclerOptions<Dataholder> options =
                    new FirebaseRecyclerOptions.Builder<Dataholder>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("comusers"), Dataholder.class)
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




