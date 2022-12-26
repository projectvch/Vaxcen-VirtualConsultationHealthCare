// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VCHSignupActivity extends AppCompatActivity {

    Spinner roll;
    String[] item = {"Doctor", "Coordinator"};

    DatabaseReference databaseReference;
    Button button;
    Button button2;
    EditText email, password, phone, name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vchsignup);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        roll = findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
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
                final Pattern special = Pattern.compile("[!.@#$%&*()_+=|<>?{}\\[\\]~-]");
                final Matcher m = special.matcher(name1);
                final boolean check = m.find();

                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(VCHSignupActivity.this, "Please enter patient Email", Toast.LENGTH_SHORT).show();
                } else if (check) {
                    Toast.makeText(VCHSignupActivity.this,
                            "Special Character and dot(.) is not allowed in username", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("ResourceType")
    private void formComplete() {
        final FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        databaseReference = rootnode.getReference("registration");

        final String email1 = email.getText().toString();
        final String password1 = password.getText().toString();
        final String phone1 = phone.getText().toString();
        final String roll1 = roll.getSelectedItem().toString();
        final String name1 = name.getText().toString().toLowerCase();

        final DataholderSignup dataholder = new DataholderSignup(name1, email1, password1, phone1, roll1);
        databaseReference.child(name.getText().toString().toLowerCase()).setValue(dataholder);
        email.setText("");
        password.setText("");
        phone.setText("");
        name.setText("");
        final AlertDialog.Builder builder = new AlertDialog.Builder(VCHSignupActivity.this);
        //builder.setTitle(name1 + " has successfully registered !");
        builder.setView(R.layout.signup_message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                final Intent intent = new Intent(VCHSignupActivity.this, VCHLoginActivity.class);
                startActivity(intent);
            }
        });

        builder.create().show();
        //Toast.makeText(VCHSignupActivity.this, "User is Successfully Registered", Toast.LENGTH_LONG).show();
    }
}

