// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.azure.samples.communication.calling.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VCHSignupActivity extends AppCompatActivity {

    Spinner roll;
    String[] item = {"Doctor", "Coordinator"};

    DatabaseReference databaseReference;
    Button button;
    Button button2;
    EditText email, password, phone, name;
    int maxid = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vchsignup);
        getSupportActionBar().hide();
        roll = findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roll.setAdapter(adapter);

        roll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent,
                                       final View view, final int position, final long l) {
                final String value = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(final AdapterView<?> adapterView) {

            }
        });

        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final String email1 = email.getText().toString();
                final String password1 = password.getText().toString();
                final String phone1 = phone.getText().toString();
                final String roll1 = roll.getSelectedItem().toString();
                final String name1 = name.getText().toString().toLowerCase();
                final Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
                final Matcher m = special.matcher(name1);
                final boolean check = m.find();

                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(VCHSignupActivity.this, "Please enter patient Email", Toast.LENGTH_SHORT).show();
                } else if (check) {
                    Toast.makeText(VCHSignupActivity.this,
                            "Special Character is allow in username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone.getText().toString())) {
                    Toast.makeText(VCHSignupActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(VCHSignupActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email1) || TextUtils.isEmpty(password1)) {
                    Toast.makeText(VCHSignupActivity.this,
                            "Enter Email and Password Both !", Toast.LENGTH_SHORT).show();
                } else if (password1.length() < 6) {
                    Toast.makeText(VCHSignupActivity.this,
                            "Password should be in 6 or more character", Toast.LENGTH_SHORT).show();
                } else {
                    formComplete();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openVCHLoginActivity();
            }
        });
                
    }
    public void openVCHLoginActivity() {
        final Intent intent = new Intent(this, VCHLoginActivity.class);
        startActivity(intent);
    }

    private void formComplete() {
        final FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        databaseReference = rootnode.getReference("newregistration");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                maxid = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError error) {

            }
        });

        final String email1 = email.getText().toString();
        final String password1 = password.getText().toString();
        final String phone1 = phone.getText().toString();
        final String roll1 = roll.getSelectedItem().toString();
        final String name1 = name.getText().toString().toLowerCase();

        final DataholderSignup dataholder = new DataholderSignup(name1, email1, password1, phone1, roll1);
        databaseReference.child(String.valueOf(maxid + 1)).setValue(dataholder);
        email.setText("");
        password.setText("");
        phone.setText("");
        name.setText("");
        Toast.makeText(VCHSignupActivity.this, "User is Successfully Registered", Toast.LENGTH_LONG).show();
    }
}
