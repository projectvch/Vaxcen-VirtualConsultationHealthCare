// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends FirebaseRecyclerAdapter<Dataholder, MyAdapter.Myviewholder> {

    int maxid = 0;

    public MyAdapter(@NonNull final FirebaseRecyclerOptions<Dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final Myviewholder holder, final int position,
                                    @NonNull final Dataholder model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.disease.setText(model.getDisease());
        holder.phone.setText(model.getPhone());
        holder.symptoms.setText(model.getSymptoms());
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());
        holder.register.setText(model.getRegister());
        Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);


        final Map<String, Object> map = new HashMap<>();
        final Map<String, Object> map1 = new HashMap<>();
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
                final EditText disease = myview.findViewById(R.id.udisease);
                final EditText symptoms = myview.findViewById(R.id.usymptoms);
                final Button submit = myview.findViewById(R.id.usubmit);

                purl.setText(model.getPimage());
                name.setText(model.getName());
                date.setText(model.getDate());
                address.setText(model.getAddress());
                email.setText(model.getEmail());
                phone.setText(model.getPhone());
                disease.setText(model.getDisease());
                symptoms.setText(model.getSymptoms());

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
                        map.put("disease", disease.getText().toString());
                        map.put("symptoms", symptoms.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("newusers")
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

                        //final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        //final String compcalldate = String.valueOf(timestamp.getTime());
                        FirebaseDatabase.getInstance()
                                .getReference().child("newcomuser").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot snapshot) {
                                        maxid = (int) snapshot.getChildrenCount();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull final DatabaseError error) {

                                    }
                                });

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("date").setValue(model.date);

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("img").setValue(model.pimage);

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("address").setValue(model.address);

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("email").setValue(model.email);

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("disease").setValue(model.disease);

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("symptoms").setValue(model.symptoms);

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("register").setValue(model.register);

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("phone").setValue(model.phone);

                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                .child(String.valueOf(maxid + 1)).child("name").setValue(model.name)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(final Void aVoid) {
                                        FirebaseDatabase.getInstance().getReference().child("newusers")
                                                .child(getRef(position).getKey()).removeValue();
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

                        //final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        //final String cancelcalldate = String.valueOf(timestamp.getTime());

                        FirebaseDatabase.getInstance()
                                .getReference().child("newcancelappointment")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot snapshot) {
                                        maxid = (int) snapshot.getChildrenCount();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull final DatabaseError error) {

                                    }
                                });

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("date").setValue(model.date);

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("img").setValue(model.pimage);

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("address").setValue(model.address);

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("email").setValue(model.email);

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("disease").setValue(model.disease);

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("symptoms").setValue(model.symptoms);

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("register").setValue(model.register);

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("phone").setValue(model.phone);

                        FirebaseDatabase.getInstance().getReference().child("newcancelappointment")
                                .child(String.valueOf(maxid + 1)).child("name").setValue(model.name)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(final Void aVoid) {
                                        FirebaseDatabase.getInstance().getReference().child("newusers")
                                                .child(getRef(position).getKey()).removeValue();
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
        TextView name, phone, email, disease, symptoms, address, date, register;
        Myviewholder(@NonNull final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            phone = (TextView) itemView.findViewById(R.id.phonetext);
            email = (TextView) itemView.findViewById(R.id.emailtext);
            symptoms = (TextView) itemView.findViewById(R.id.symptomstext);
            disease = (TextView) itemView.findViewById(R.id.diseasetext);
            address = (TextView) itemView.findViewById(R.id.address);
            date = (TextView) itemView.findViewById(R.id.date);
            register = (TextView) itemView.findViewById(R.id.register);
            edit = (ImageView) itemView.findViewById(R.id.editicon);
            deleteicon = (ImageView) itemView.findViewById(R.id.deleteicon);
            cancel = (ImageView) itemView.findViewById(R.id.cancel);
        }
    }
}
