// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.azure.samples.communication.calling.R;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter2 extends FirebaseRecyclerAdapter<Dataholder, MyAdapter2.Myviewholder> {


    static  String cordname;
    static  String patname;
    static  String regdate;
    static  boolean b1;
    Connection connect;
    String connectionresult = "";
    String loccordname;
    String locpatname;
    String locregdate;

    public MyAdapter2(@NonNull final FirebaseRecyclerOptions<Dataholder> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final Myviewholder holder, final int position,
                                    @NonNull final Dataholder model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.lastname.setText(model.getLastname());
        holder.middlename.setText(model.getMiddlename());
        holder.phone.setText(model.getPhone());
        holder.age.setText(model.getAge() + " years");
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());
        holder.waitdate.setText(model.getWaitingdate());
        holder.register.setText(model.getRegister());
        holder.feaver.setText(model.getFeaver());
        holder.vaccine.setText(model.getVaccine());
        holder.hiv.setText(model.getHiv());
        holder.allergic.setText(model.getAllergic());
        holder.immunoglob.setText(model.getImmunoglob());
        holder.pregnant.setText(model.getPregnant());
        Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);



        final Map<String, Object> map = new HashMap<>();
        final Map<String, Object> map1 = new HashMap<>();

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder ab = new AlertDialog.Builder(view.getContext());
                //Added by Deepak
                cordname = model.register;
                patname  = model.name;
                regdate = model.date;
                b1 = true;


                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_intro_view))
                        .setExpanded(true, 1600)
                        .create();
                Log.d("value", String.valueOf(cordname));
                Log.d("value", String.valueOf(patname));
                ab.setMessage("Initiate Call");
                ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {

                        final Intent in = new Intent(view.getContext(), IntroViewActivity.class);
                        view.getContext().startActivity(in);
                    }
                });
                ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {

                    }
                });
                ab.create().show();

            }
        });


        holder.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.img.getContext());
                loccordname = model.register;
                locpatname  = model.name;
                locregdate = model.date;
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want mark this patient as completed ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        map.put("status", "completed");
                        FirebaseDatabase.getInstance().getReference().child("patient_information")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(final Void aVoid) {
                                        updatetokenstorage();
                                        Log.d("success", "Canceled Appointment");

                                    }
                                });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {

                    }
                });

                builder.show();
            }
        });

    }

    public void updatetokenstorage() {



        try {
            final ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if (connect != null) {

                final String updquery = "update dbo.tokenstorage set status = 'Completed' where coordinatorname = '"
                        + loccordname + "' and patientname = '" + locpatname + "' and regdate = '" + locregdate + "'";
                final Statement updst = connect.createStatement();
                final ResultSet updrs = updst.executeQuery(updquery);



            } else {
                connectionresult = "Check Connection";
            }

        } catch (Exception ex) {



        }


    }



    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singledata2, parent, false);
        return new Myviewholder(view);
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView edit;
        ImageView deleteicon;
        TextView name, phone, email, lastname, middlename, age, address, date, register, waitdate,
                feaver, vaccine, allergic, hiv, immunoglob, pregnant;
        Myviewholder(@NonNull final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            phone = (TextView) itemView.findViewById(R.id.phonetext);
            email = (TextView) itemView.findViewById(R.id.emailtext);
            age = (TextView) itemView.findViewById(R.id.agetext);
            lastname = (TextView) itemView.findViewById(R.id.lnametext);
            middlename = (TextView) itemView.findViewById(R.id.mnametext);
            address = (TextView) itemView.findViewById(R.id.addresstext);
            date = (TextView) itemView.findViewById(R.id.datetext);
            waitdate = (TextView) itemView.findViewById(R.id.waitingdatetext);
            register = (TextView) itemView.findViewById(R.id.registertext);
            feaver = (TextView) itemView.findViewById(R.id.feavertext);
            vaccine = (TextView) itemView.findViewById(R.id.vaccinetext);
            allergic = (TextView) itemView.findViewById(R.id.allergictext);
            hiv = (TextView) itemView.findViewById(R.id.hivtext);
            immunoglob = (TextView) itemView.findViewById(R.id.immunoglobttext);
            pregnant = (TextView) itemView.findViewById(R.id.pregnanttext);
            edit = (ImageView) itemView.findViewById(R.id.editicon);
            deleteicon = (ImageView) itemView.findViewById(R.id.deleteicon);
        }
    }
}
