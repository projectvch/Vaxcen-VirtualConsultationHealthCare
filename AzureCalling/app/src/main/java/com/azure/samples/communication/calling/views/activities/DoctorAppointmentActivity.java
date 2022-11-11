// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.SearchView;

import com.azure.samples.communication.calling.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorAppointmentActivity extends AppCompatActivity {
    RecyclerView recview5;
    MyAdapter5 adapter5;
    DBmain dBmain;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);
        getSupportActionBar().hide();

        recview5 = (RecyclerView) findViewById(R.id.recview5);
        recview5.setLayoutManager(new LinearLayoutManager(this));

        final FirebaseRecyclerOptions<Dataholder> options =
                new FirebaseRecyclerOptions.Builder<Dataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), Dataholder.class)
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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        final MenuItem item = menu.findItem(R.id.search);

        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                processsearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(final String s) {
        final FirebaseRecyclerOptions<Dataholder> options =
                new FirebaseRecyclerOptions.Builder<Dataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").
                                orderByChild("name").startAt(s).endAt(s + "\uf8ff"), Dataholder.class)
                        .build();
        adapter5 = new MyAdapter5(options);
        adapter5.startListening();
        recview5.setAdapter(adapter5);
    }

}




