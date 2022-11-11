// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.azure.samples.communication.calling.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton homebutton;
    RecyclerView recview;
    MyAdapter adapter;
    DBmain dBmain;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        recview = (RecyclerView) findViewById(R.id.recview);
        homebutton = (ImageButton) findViewById(R.id.homebutton);
        recview.setLayoutManager(new LinearLayoutManager(this));

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                opendashboard();
            }
        });

        final FirebaseRecyclerOptions<Dataholder> options =
                new FirebaseRecyclerOptions.Builder<Dataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("newusers"), Dataholder.class)
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
        adapter = new MyAdapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);
    }

    public void opendashboard() {
        final Intent intent = new Intent(this, CoordinatorDashboardActivity.class);
        startActivity(intent);
    }

}




