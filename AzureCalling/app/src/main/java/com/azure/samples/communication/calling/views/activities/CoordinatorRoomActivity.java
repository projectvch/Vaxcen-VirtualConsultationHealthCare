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
import android.widget.ImageView;
import android.widget.SearchView;
import com.azure.samples.communication.calling.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CoordinatorRoomActivity extends AppCompatActivity {
    ImageView editicon;
    RecyclerView recview4;
    MyAdapter4 adapter4;
    DBmain dBmain;
    ArrayList<Model>modelArrayList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_room);
        getSupportActionBar().hide();

        recview4 = (RecyclerView) findViewById(R.id.recview4);
        editicon = (ImageView) findViewById(R.id.editicon);

        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openIntroViewActivity();
            }
        });
        recview4.setLayoutManager(new LinearLayoutManager(this));

        final FirebaseRecyclerOptions<Dataholder> options =
                new FirebaseRecyclerOptions.Builder<Dataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("newcomuser"), Dataholder.class)
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
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("comusers").
                                orderByChild("name").startAt(s).endAt(s + "\uf8ff"), Dataholder.class)
                        .build();
        adapter4 = new MyAdapter4(options);
        adapter4.startListening();
        recview4.setAdapter(adapter4);
    }

    public void openIntroViewActivity() {
        final Intent intent = new Intent(this, IntroView2Activity.class);
        startActivity(intent);
        finish();
    }
}
