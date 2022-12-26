// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azure.samples.communication.calling.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends FirebaseRecyclerAdapter<Dataholder, MyAdapter.Myviewholder> {

    public MyAdapter(@NonNull final FirebaseRecyclerOptions<Dataholder> options) {
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
        final Map<String, Object> map2 = new HashMap<>();
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true, 1600)
                        .create();
                final View myview = dialogPlus.getHolderView();
                final EditText purl = myview.findViewById(R.id.uimgurl);
                final EditText name = myview.findViewById(R.id.uname);
                final EditText date = myview.findViewById(R.id.udate);
                final EditText address = myview.findViewById(R.id.uaddress);
                final EditText email = myview.findViewById(R.id.uemail);
                final EditText phone = myview.findViewById(R.id.uphone);
                final EditText lastname = myview.findViewById(R.id.ulname);
                final EditText middlename = myview.findViewById(R.id.umname);
                final EditText age = myview.findViewById(R.id.uage);
                final Button submit = myview.findViewById(R.id.usubmit);

                purl.setText(model.getPimage());
                name.setText(model.getName());
                date.setText(model.getDate());
                address.setText(model.getAddress());
                email.setText(model.getEmail());
                phone.setText(model.getPhone());
                lastname.setText(model.getLastname());
                middlename.setText(model.getMiddlename());
                age.setText(model.getAge());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        map.put("purl", purl.getText().toString());
                        map.put("name", name.getText().toString());
                        map.put("date", date.getText().toString());
                        map.put("address", address.getText().toString());
                        map.put("email", email.getText().toString());
                        map.put("phone", phone.getText().toString());
                        map.put("lastname", lastname.getText().toString());
                        map.put("middlename", middlename.getText().toString());
                        map.put("age", age.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("patient_information")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(final Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull final Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }
        });

        holder.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to send patient in waiting room ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        map1.put("status", "waiting");
                        final String date = DateFormat.getInstance().format(System.currentTimeMillis());
                        map1.put("waitingdate", date);
                        FirebaseDatabase.getInstance().getReference().child("patient_information")
                                .child(getRef(position).getKey()).updateChildren(map1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(final Void aVoid) {
                                        Log.d("success", "added to waiting room");
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

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to cancel this appointment ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        map2.put("status", "canceled");
                        FirebaseDatabase.getInstance().getReference().child("patient_information")
                                .child(getRef(position).getKey()).updateChildren(map2)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(final Void aVoid) {
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

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singledata, parent, false);
        return new Myviewholder(view);
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView edit;
        ImageView deleteicon;
        ImageView cancel;
        TextView name, phone, email, lastname, middlename, age, address, date, register,
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
            register = (TextView) itemView.findViewById(R.id.registertext);
            feaver = (TextView) itemView.findViewById(R.id.feavertext);
            vaccine = (TextView) itemView.findViewById(R.id.vaccinetext);
            allergic = (TextView) itemView.findViewById(R.id.allergictext);
            hiv = (TextView) itemView.findViewById(R.id.hivtext);
            immunoglob = (TextView) itemView.findViewById(R.id.immunoglobttext);
            pregnant = (TextView) itemView.findViewById(R.id.pregnanttext);
            edit = (ImageView) itemView.findViewById(R.id.editicon);
            deleteicon = (ImageView) itemView.findViewById(R.id.deleteicon);
            cancel = (ImageView) itemView.findViewById(R.id.cancel);
        }
    }
}
