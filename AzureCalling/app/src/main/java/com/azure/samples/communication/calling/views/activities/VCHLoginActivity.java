// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.azure.samples.communication.calling.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VCHLoginActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_prefs";
    static  String regby;
    static String tokenstore;
    static String doctorname;
    static String emailkey = "email_key";
    static String passkey = "password_key";
    static String roll;

    private static String page;
    FirebaseAuth auth;
    SharedPreferences sharedpreferences;
    String email, pass;
    EditText name, password;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button button;
    private Button button2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vchlogin);
        getSupportActionBar().hide();
        //Added by Deepak
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        auth = FirebaseAuth.getInstance();
        email = sharedpreferences.getString("email_key", null);
        pass = sharedpreferences.getString("password_key", null);
        /*if (sharedpreferences.contains(email)) {
            final Intent intent = new Intent(VCHLoginActivity.this, CoordinatorDashboardActivity.class);
            startActivity(intent);
        }*/


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String name1 = name.getText().toString().toLowerCase();
                final String password1 = password.getText().toString();
                final int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                final String subject1 = String.valueOf(radioButton.getText());


                final Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
                final Matcher m = special.matcher(name1);
                final boolean check = m.find();



                if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(password1)) {
                    Toast.makeText(VCHLoginActivity.this,
                            "Enter Email and Password Both !", Toast.LENGTH_SHORT).show();
                } else if (check) {
                    Toast.makeText(VCHLoginActivity.this,
                            "Special Character is allow in username", Toast.LENGTH_SHORT).show();
                } else if (password1.length() < 6) {
                    Toast.makeText(VCHLoginActivity.this,
                            "Password should be in 6 or more character", Toast.LENGTH_SHORT).show();
                } else if (name1.contains(" ")) {
                    Toast.makeText(VCHLoginActivity.this,
                            "You have enter wrong email !", Toast.LENGTH_SHORT).show();
                } else if (name1.startsWith(" ")) {
                    Toast.makeText(VCHLoginActivity.this,
                            "You have enter wrong email !", Toast.LENGTH_SHORT).show();
                } else {
                    final SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(emailkey, name.getText().toString());
                    editor.putString(passkey, password.getText().toString());
                    editor.apply();

                    login(name1, password1, subject1);
                    if (subject1.equals("Coordinator")) {

                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull final Task<String> task) {
                                        if (task.isSuccessful()) {
                                            tokenstore = task.getResult();
                                            //token.setText(tokenstore);
                                            Log.d("toooo", "token:" + tokenstore);

                                        }
                                    }
                                });
                    }
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openVCHSignupActivity();

            }
        });
    }
    public void login(final String name, final String password, final String subject1) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("registration");
        Log.d("value", String.valueOf(reference.child(name)));

        reference.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    final String checkroll = snapshot.child("roll").getValue(String.class);
                    //Log.d("value", String.valueOf(checkroll));
                    if (checkroll.equals(subject1)) {
                        if (checkroll.equals("Coordinator")) {
                            regby = name;
                            roll = "Coordinator";
                            Toast.makeText(VCHLoginActivity.this,
                                    "Login Successfully", Toast.LENGTH_SHORT).show();
                            final Intent intent = new Intent(VCHLoginActivity.this,
                                                    CoordinatorDashboardActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();



                        } else if (checkroll.equals("Doctor")) {
                            roll = "Doctor";
                            Toast.makeText(VCHLoginActivity.this,
                                                    "Login Successfully", Toast.LENGTH_SHORT).show();
                            final Intent intent = new Intent(VCHLoginActivity.this,
                                                    DoctorDashboardActivity.class);
                            startActivity(intent);
                            finish();
                            doctorname = name;
                        }
                    } else {
                        Toast.makeText(VCHLoginActivity.this,
                                "Login Failed", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(VCHLoginActivity.this,
                            "This user is not available !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError error) {

                Toast.makeText(VCHLoginActivity.this,
                        error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openVCHSignupActivity() {
        final Intent intent = new Intent(this, VCHSignupActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("value3", String.valueOf(roll));
        if (roll != null) {
            if (roll.equals("Coordinator")) {
                final Intent i = new Intent(VCHLoginActivity.this, CoordinatorDashboardActivity.class);
                startActivity(i);
            } else if (roll.equals("Doctor")) {
                final Intent i = new Intent(VCHLoginActivity.this, DoctorDashboardActivity.class);
                startActivity(i);
            }
        }
    }

}
